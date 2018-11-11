package com.distributed.ta;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("NodeName")
public class NodeName {


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
