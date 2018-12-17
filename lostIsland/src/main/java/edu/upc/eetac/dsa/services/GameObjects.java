package edu.upc.eetac.dsa.services;

import io.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(value="/objects", description = "Service of Autentication")
@Path("/objects")
public class GameObjects {
    final static Logger log = Logger.getLogger(GameObjects.class.getName());

    @GET
    @Path("basic")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}