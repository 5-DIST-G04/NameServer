package com.distributed.ta;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("FileLocation")
public class FileLocation {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public File getIp(@QueryParam("fileName") String fileName) {
        File file = new File();
        file.setFileName(fileName);
        NodeNameDatabase.getInstance().getFileLocation(file);
        return file;
    }
}
