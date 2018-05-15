package com.albertosoto.magnolia.bigdata.data.generic;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.cms.util.QueryUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.iterator.FilteringPropertyIterator;
import info.magnolia.jcr.predicate.AbstractPredicate;
import info.magnolia.jcr.predicate.JCRPropertyHidingPredicate;
import info.magnolia.jcr.util.MetaDataUtil;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.groovy.support.nodes.MgnlGroovyJCRNode;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.templating.functions.TemplatingFunctions;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.jackrabbit.commons.predicate.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.query.Query;
import java.util.*;

/**
 * @author Asoto
 * Date: 26/09/12 14:25
 * Description:
 * <p>
 * Nueva posibilidad: Hacer uno nuevo para gestionar por ioc
 */
public class GenericJCRHelper {
    private static final Logger log = LoggerFactory.getLogger(GenericJCRHelper.class);
    private static final String defaultSite = "default";

    /**
     * Node filter accepting everything except nodes with namespace jcr (version and system store).
     */
    public static Predicate ALL_NODES_EXCEPT_SUBPAGES = new AbstractPredicate<Node>() {
        @Override
        public boolean evaluateTyped(Node node) {
            try {
                //exclude metadata &&
                return !node.getName().startsWith(MgnlNodeType.JCR_PREFIX)
                        && !NodeUtil.isNodeType(node, MgnlNodeType.NT_METADATA)
                        && !NodeUtil.isNodeType(node, MgnlNodeType.NT_PAGE);
            } catch (RepositoryException e) {
                return false;
            }
        }
    };


    /**
     * Devuelve una property sin generar errores.
     * Hace el cast de forma correcta para el valor y lo transforma a String
     *
     * @param node     nodo donde buscar
     * @param property propiedad
     * @return
     */
    public static String getEmptyPropertyValue(Node node, String property) {
        String rtnValue = StringUtils.EMPTY;
        try {
            if (node != null && node.hasProperty(property)) {
                rtnValue = StringUtils.isEmpty(node.getProperty(property).getString())
                        ? StringUtils.EMPTY
                        : node.getProperty(property).getString();
            }
            //       node.getProperty(property).getString();
        } catch (RepositoryException e) {
            try {
                throw new RuntimeException(String.format("Can't read value of property %1$s from node %2$s",
                        property,
                        node.getName()),
                        e);
            } catch (RepositoryException e1) {
                throw new RuntimeException("Can't read value of property " + property, e);
            }
        }
        return rtnValue;
    }

    /**
     * ASOTO: Nota importante!
     * <p>
     * Si la propiedad deseada es de tipo array (pe un checkbox multiseleccion), se debe utilizar
     * HTTTemplatingFunctions.getNodeProperties
     *
     * @param node         Nodo inicial de busqueda
     * @param propertyName Nombre de la propiedad a buscar
     * @return
     */
    public static Value getPropertyValue(Node node, String propertyName) {
        Value rtn = null;
        try {
            if (node != null && node.hasProperty(propertyName)) {
                rtn = node.getProperty(propertyName).getValue();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return rtn;
    }


    /**
     * Devuelve el site root para cualquier uuid interno
     *
     * @param uuid
     * @return
     */
    public static Node getSiteRoot(String uuid) throws RepositoryException {
        TemplatingFunctions fn = Components.getComponent(TemplatingFunctions.class);
        Node node = NodeUtil.getNodeByIdentifier(RepositoryConstants.WEBSITE, uuid);
        return fn.root(node);
    }


    public static String getPageTemplate(Node partialPage) throws RepositoryException {
        TemplatingFunctions templatingFunctions = Components.getComponent(TemplatingFunctions.class);
        return getPageTemplate(partialPage, templatingFunctions);
    }

    public static String getPageTemplate(Node partialPage, TemplatingFunctions templatingFunctions) throws RepositoryException {
        return MetaDataUtil.getMetaData(templatingFunctions.page(partialPage)).getTemplate();
    }

    public static String getNodeTemplate(Node node) {
        return MetaDataUtil.getMetaData(node).getTemplate();
    }

    /**
     * Evalua el path del nodo controlando la exception lanzada
     *
     * @param node node a buscar
     * @return path del nodo o vacío en caso contrario. Null en caso de exception.
     */
    public static String getNodePath(Node node) {
        String currentnodePath = StringUtils.EMPTY;
        try {
            if (node != null) {
                currentnodePath = node.getPath();
            }
        } catch (RepositoryException e) {
            currentnodePath = null;
        }
        return currentnodePath;
    }

    public static Node groovyNodeToJCR(MgnlGroovyJCRNode groovyNode) {
        if (groovyNode != null) {
            return groovyNode.getWrappedNode();
        }
        return null;
    }


    /**
     * Generic function for loading node content stored as multiple values in a property
     * It's mandatory to define a repository, and supports loading previous content from a container,
     * for building a bigger collection
     *
     * @param node              Node to get property value
     * @param property          Property name
     * @param repository        Not empty repository from RepositoryConstants
     * @param previousContainer previous loaded container
     * @return new collection with all values stored by UUID
     */
    public static TreeMap<String, Node> getNodesFromProperty(Node node, String property, String repository, TreeMap<String, Node> previousContainer) {
        if (StringUtils.isNotEmpty(repository)) {
            TreeMap<String, Node> rtn = new TreeMap<String, Node>();
            if (previousContainer != null && previousContainer.size() > 0) {
                rtn.putAll(previousContainer);
            }
            try {
                Session session = MgnlContext.getInstance().getJCRSession(repository);
                Value[] tags = getValues(node, property);
                if (tags != null) {
                    for (Value value : tags) {
                        String key = value.getString();
                        //if previous dictionary does not contain this value, let's try to load and store it
                        if (!rtn.containsKey(key) || rtn.get(key) == null) {
                            try {
                                Node dataNode = session.getNodeByIdentifier(key);
                                if (dataNode != null) {
                                    rtn.put(key, dataNode);
                                }
                            } catch (ItemNotFoundException notFoundEx) {
                                log.warn("Item not found at repository " + repository + " with UUID " + key, notFoundEx);
                            }
                        }
                    }
                }
            } catch (RepositoryException e) {
                log.error("getNodesFromProperty", e);
            }
            return rtn;
        }
        return null;
    }

    /**
     * Returns a valid collection if a property exists in a node.
     * Valid for multiple or single properties
     *
     * @param node     Current node to search
     * @param property Valid property
     * @return Array of values in the property, creating a new array for single properties
     * @throws RepositoryException
     */
    public static Value[] getValues(Node node, String property) throws RepositoryException {
        if (node.hasProperty(property)) {
            return (node.getProperty(property).isMultiple()) ? node.getProperty(property).getValues() : new Value[]{node.getProperty(property).getValue()};
        } else {
            return null;
        }
    }

    public void setNodeProperty(Node node, String property, String value) {
        if (node != null && StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(value)) {
            try {
                node.setProperty(property, value);
            } catch (RepositoryException e) {
                log.warn("Htt - Could not set Node property", e);
            }
        }
    }

    public Collection<Node> doSimpleJCRQuery(String query) {
        return doJCRQuery(StringUtils.EMPTY, StringUtils.EMPTY, query);
    }

    public Collection<Node> doJCRQuery(String container, String expectedReturnType, String query) {
        Collection<Node> rtnValue = new ArrayList<Node>();
        try {
            //defaultValues
            String workspace = StringUtils.isNotEmpty(container) ? container : RepositoryConstants.WEBSITE;
            String returnType = StringUtils.isNotEmpty(expectedReturnType) ? expectedReturnType : MgnlNodeType.NT_PAGE;
            //launchQuery
            rtnValue = NodeUtil.getCollectionFromNodeIterator(QueryUtil.search(workspace, query, Query.JCR_SQL2, returnType));
        } catch (RepositoryException e) {
            log.error("<HTTDataModel> -  JCRQuery => " + query, e);
        }
        return rtnValue;
    }


    /**
     * Para encontrarlo correctamente ....
     *
     * @param root
     * @return
     */
    public List<Node> getAllChildNodes(Node root) throws RepositoryException {
        return NodeUtil.asList(NodeUtil.collectAllChildren(root, NodeUtil.EXCLUDE_META_DATA_FILTER));
    }

    /**
     * Importante: Código Hocus Pocus
     * <p>
     * Gets an iterator for properties inside node. Return null if any exception happens
     *
     * @param node Target node
     * @return Iterator for al properties inside node excluding metadata properties
     */
    public static Iterator getAllNodeProperties(Node node) {
        if (node != null) {
            try {
                return new FilteringPropertyIterator(node.getProperties(), new JCRPropertyHidingPredicate());
            } catch (RepositoryException e) {
                log.error("Error while Filtering node properties", e);
            }
        }
        return null;
    }

    public List<Node> getFirstChildNodes(Node root) throws RepositoryException {
        return NodeUtil.asList(NodeUtil.getNodes(root, NodeUtil.EXCLUDE_META_DATA_FILTER));
    }

    public List<Node> getAllDataNodeDescendants(String path) {
        try {
            if (StringUtils.isNotEmpty(path)) {
                Session session = MgnlContext.getJCRSession("data");
                Node root = session.getNode(path);
                return getAllChildNodes(root);
            }
        } catch (RepositoryException e) {
            log.error("Error al cargar allNodeDescendants. Null value devuelto.", e);
        }
        return null;
    }

    public List<Node> getFirstDataNodeChilds(String path) {
        try {
            if (StringUtils.isNotEmpty(path)) {
                Session session = MgnlContext.getJCRSession("data");
                Node root = session.getNode(path);
                return getFirstChildNodes(root);
            }
        } catch (RepositoryException e) {
            log.error("Error al cargar getFirstDataNodeChilds. Null value devuelto.", e);
        }
        return null;
    }

    /**
     * Busca un nodo en el repositorio indicado. Si es nulo, en "WEBSITE"
     *
     * @param repository
     * @param uuid
     * @return
     */
    public Node loadNodeByUUID(String repository, String uuid) {
        Node rtn = null;
        String workspace = StringUtils.defaultString(repository, RepositoryConstants.WEBSITE);
        if (StringUtils.isNotEmpty(uuid)) {
            try {
                rtn = NodeUtil.getNodeByIdentifier(workspace, uuid);
            } catch (RepositoryException e) {
                log.error("Can't load node from uuid: '" + uuid + " at repo:" + repository, e.toString());
            }
        }
        return rtn;
    }

    public Session getJCRSession(String repository) throws RepositoryException {
        String workspace = StringUtils.defaultString(repository, RepositoryConstants.WEBSITE);
        return MgnlContext.getJCRSession(workspace);
    }

    /**
     * Utilidad para "chosenSelect"
     * A partir de un valor con una lista de UUIDs separados por "," carga cada uno de los nodos del repositorio seleccionado
     *
     * @param repository "website" por defecto
     * @param uuidList   Valor String con UUIDs separados por ","
     * @return Todos los nodos cargados
     */
    public List<Node> loadNodeCollectionByUUID(String repository, String uuidList) {
        ArrayList<Node> lst = new ArrayList<Node>();
        String validRepository = StringUtils.defaultIfEmpty(repository, RepositoryConstants.WEBSITE);
        if (StringUtils.isNotEmpty(uuidList)) {
            String[] values = StringUtils.split(StringUtils.strip(uuidList), ",");
            for (String selection : values) {
                lst.add(loadNodeByUUID(repository, selection));
            }
            return lst;
        }
        return null;
    }


    public static Node getNodeByURI(Node initialNode, String URI, String locale) throws RepositoryException {
        String mURI = URI.replace(".html", "");
        if (mURI.endsWith("/")) {
            mURI = StringUtils.substringBeforeLast(mURI, "/");
        }
        if (mURI.startsWith("/")) {
            mURI = StringUtils.substringAfter(mURI, "/");
        }

        if (mURI.contains("/")) {
            mURI = StringUtils.substringAfter(mURI, "/");
        }

        if (mURI.contains("/" + locale + "/")) {
            mURI = StringUtils.substringAfter(mURI, locale + "/");
        }

        if (!StringUtils.isEmpty(mURI) && initialNode.hasNode(mURI)) {
            return initialNode.getNode(mURI);
        } else {
            mURI = StringUtils.substringAfter(mURI, initialNode.getName() + "/");
            if (!StringUtils.isEmpty(mURI) && initialNode.hasNode(mURI)) {
                return initialNode.getNode(mURI);
            }
        }

        return initialNode;
    }

}
