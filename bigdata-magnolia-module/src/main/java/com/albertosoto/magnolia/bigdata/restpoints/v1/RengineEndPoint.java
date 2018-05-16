package com.albertosoto.magnolia.bigdata.restpoints.v1;

import com.albertosoto.magnolia.bigdata.data.generic.DataTableWrapper;
import com.albertosoto.magnolia.bigdata.spring.RenjinHelper;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
import info.magnolia.templating.jsonfn.JsonTemplatingFunctions;
import org.apache.commons.collections4.CollectionUtils;

import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * com.albertosoto.magnolia.bigdata.restpoints.v1
 * Class RengineEndPoint
 * By berto. 10/05/2018
 * <p>
 * Rest end point example via the magnolia way for r-script language
 */
@Path("/r-engine/v1")
public class RengineEndPoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {
    private JsonTemplatingFunctions jsonfn;

    public RengineEndPoint(D endpointDefinition, JsonTemplatingFunctions jsonfn) {
        super(endpointDefinition);
        this.jsonfn = jsonfn;
    }

    @Path("/getScripts")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getScripts() {
        RenjinHelper renjin = RenjinHelper.getInstance();
        DataTableWrapper<String> response = new DataTableWrapper<>();
        try {
            response.setData(renjin.getScriptPaths());
        } catch (RepositoryException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(response).build();
    }


    @Path("/execute/{script}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response executeScript(
            @PathParam("script") String script
    ) {
        try {
            List<String> result = new ArrayList<>();
            RenjinHelper renjin = RenjinHelper.getInstance();
            DataTableWrapper<String> response = new DataTableWrapper<>();
            CollectionUtils.addAll(result, renjin.executeScript(script));
            response.setData(result);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}