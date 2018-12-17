package edu.upc.eetac.dsa.services;


import io.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(value="/text", description = "Endpoint to Order Text")
@Path("/text")
public class Text {
    final static Logger log = Logger.getLogger(Text.class.getName());

    @GET
    @Path("basic")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}
