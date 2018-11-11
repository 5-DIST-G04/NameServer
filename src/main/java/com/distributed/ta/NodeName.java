package com.distributed.ta;

import javax.ws.rs.*;
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
    public Node getIp(@QueryParam("name") String name) {
        Node node = new Node();
        node.setName(name);
        NodeNameDatabase.getInstance().getNodeIp(node);
        return node;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addNode(Node node){
        if(node.getIpAddress() == null | node.getName() == null | node.getName().equals("") | node.getIpAddress().equals(""))
            return "failed the received node object was incomplete";
        NodeNameDatabase.getInstance().addNode(node);
        return "the node has been added to the list of known nodes";
    }

}
