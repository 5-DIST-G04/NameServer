package com.distributed.ta;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.PrivateKey;


@Path("NodeName")
public class NodeName {


    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIp(@PathParam("name") String name) {
        Node node = new Node();
        node.setName(name);
        if(NodeNameDatabase.getInstance().getNodeIp(node)== -1)
            return Response.status(404).build();
        return Response.status(200).entity(node).build();
    }

    @GET
    @Path("/{name}/neighbours")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNeigbours(@PathParam("name") String name){
        NodeNameDatabase db = NodeNameDatabase.getInstance();
        db.calcNeigbours();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addNode(Node node){
        if(node.getIpAddress() == null | node.getName() == null | node.getName().equals("") | node.getIpAddress().equals(""))
            return Response.status(406).entity("failed the received node object was incomplete").build();
        if(NodeNameDatabase.getInstance().NodeExists(node))
            return Response.status(409).entity("this node already exists").build();
        NodeNameDatabase.getInstance().addNode(node);
        return Response.status(201).entity("the node has been added to the list of known nodes").build();
    }


    @DELETE
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeNode(@PathParam("name") String name){
        String output = NodeNameDatabase.getInstance().removeNode(new Node(name, ""));
        if(output == null){
            return Response.status(409).entity("the node did not exist").build();
        }
        return Response.status(204).build();
    }



}
