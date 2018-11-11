package com.distributed.ta;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("NodeName")
public class NodeName {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Node getIt(@QueryParam("name") String name) {
        Node node = new Node();
        node.setName(name);
        NodeNameDatabase.getInstance().getNodeIp(node);
        return node;
    }
}
