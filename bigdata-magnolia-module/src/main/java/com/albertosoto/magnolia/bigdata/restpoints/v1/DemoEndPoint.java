package com.albertosoto.magnolia.bigdata.restpoints.v1;

import com.albertosoto.magnolia.bigdata.data.generic.DataTableWrapper;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
import org.apache.commons.collections4.CollectionUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * com.albertosoto.magnolia.bigdata.restpoints.v1
 * Class DemoEndPoint
 * By berto. 08/05/2018
 * <p>
 *     Dummy hello world example for magnolia cms rest end points
 *
 * https://documentation.magnolia-cms.com/display/DOCS56/How+to+create+a+custom+Java-based+REST+endpoint
 * https://documentation.magnolia-cms.com/display/DOCS56/REST+module
 */
@Path("/demo/v1")
public class DemoEndPoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

    public DemoEndPoint(D endpointDefinition) {
        super(endpointDefinition);
    }

    @Path("/hello")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response hello() {
        DataTableWrapper<String> rtn = new DataTableWrapper<>();
        List<String> data = new ArrayList<>();
        CollectionUtils.addAll(data, new String[]{"test1", "test2"});
        rtn.setData(data);
        return Response.ok(rtn).build();
    }
}