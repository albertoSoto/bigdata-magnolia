package com.albertosoto.magnolia.bigdata.spring;

import com.albertosoto.magnolia.bigdata.data.generic.GenericJCRHelper;
import com.albertosoto.magnolia.bigdata.spring.service.RenjinService;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.predicate.AbstractPredicate;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

/**
 * com.albertosoto.magnolia.bigdata.spring
 * Class RenjinHelper
 * By berto. 10/05/2018
 * <p>
 * <p>
 * Uses Renjin Service in the magnolia way
 */
public class RenjinHelper {
    private static final Logger log = LoggerFactory.getLogger(GenericJCRHelper.class);
    private static RenjinHelper instance = new RenjinHelper();
    private RenjinService renjinService;
    public static final String R_ENGINE_REPO = "r-engine";
    public static final String R_SCRIPT_NAME_PROPERTY = "name";
    public static final String R_SCRIPT_CONTENT_PROPERTY = "text";


    /**
     * Node filter accepting all nodes of a type with namespace r-engine
     */
    public static AbstractPredicate<Node> RENGINE_FILTER = new AbstractPredicate<Node>() {
        @Override
        public boolean evaluateTyped(Node node) {
            try {
                String nodeTypeName = node.getPrimaryNodeType().getName();
                return nodeTypeName.startsWith("r-");
            } catch (RepositoryException e) {
                log.error("Unable to read nodeType for node");
            }
            return false;
        }
    };

    /**
     * Singleton behaviour(Service doesn't like private constructors!)
     * TODO Use IOC or Spring service capabilities
     */
    private RenjinHelper() {
        renjinService = new RenjinService();
    }

    /**
     * @return instance via Singleton
     */
    public static RenjinHelper getInstance() {
        return instance;
    }

    /**
     * Checks in repository and extract all script nodes
     *
     * @return R-Script nodes
     */
    public Iterable<Node> getScriptNodes() {
        try {
            Session session = MgnlContext.getJCRSession(R_ENGINE_REPO);
            return NodeUtil.collectAllChildren(session.getRootNode(), RENGINE_FILTER);
        } catch (RepositoryException e) {
            log.error("No r-script found", e);
            return null;
        }
    }

    /**
     * Checks in repository and extract all script paths
     *
     * @return R-Script node paths
     * @throws RepositoryException
     */
    public List<String> getScriptPaths() throws RepositoryException {
        List<String> result = new ArrayList<>();
        try {
            for (Node n : getScriptNodes()) {
                result.add(n.getPath());
            }
        } catch (RepositoryException e) {
            log.error("Accessing r-script paths", e);
            throw e;
        }
        return result;
    }

    public String[] loadScript(String path) throws Exception {
        Session session;
        final String EOL = "\n"; //use System.lineSeparator()?
        final String INITIAL_PATH = "/";
        try {
            if (StringUtils.isNotEmpty(path)) {
                session = MgnlContext.getJCRSession(RenjinHelper.R_ENGINE_REPO);
                Node parent = session.getRootNode();
                Node content = parent.getNode(StringUtils.removeStart(path, INITIAL_PATH));
                String scriptValue = PropertyUtil.getPropertyOrNull(content, R_SCRIPT_CONTENT_PROPERTY).getString();
                return scriptValue.split(EOL);
            }
        } catch (RepositoryException e) {
            log.error("Error accesing r-script {}", path, e);
            throw e;
        }
        return null;
    }

    /**
     * Executes r-script
     *
     * @param path nodepath. Removes starting slash "/Hello-world" > "Hello-world"
     * @return Split result by lines
     */
    public String[] executeScript(String path) throws Exception{
        final String EOL = "\n"; //use System.lineSeparator()?
        try {
            String[] fileContent = loadScript(path);
            if (fileContent!=null) {
                RenjinService srv = new RenjinService();
                return renjinService.executeRenjin(fileContent).split(EOL);
            }
        } catch (ScriptException e) {
            log.error("Error executing r-script {}", path, e);
            throw e;
        }
        return new String[]{};
    }


}
