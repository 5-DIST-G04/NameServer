package com.distributed.ta;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("FileLocation")
public class FileLocation {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIp(@QueryParam("fileName") String fileName) {
        File file = new File();
        file.setFileName(fileName);
        NodeNameDatabase.getInstance().getFileLocation(file);
        return Response.status(200).entity(file).build();
    }
}
