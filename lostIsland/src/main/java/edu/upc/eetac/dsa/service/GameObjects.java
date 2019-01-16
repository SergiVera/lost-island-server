package edu.upc.eetac.dsa.service;

import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import edu.upc.eetac.dsa.model.GameObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value="/objects", description = "Service of Autentication")
@Path("/objects")
public class GameObjects {
    final static Logger log = Logger.getLogger(GameObjects.class.getName());

    private ProductManager productManager;

    public GameObjects(){
        this.productManager = ProductManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "get all objects in the game", notes = "get all objects in the game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = GameObject.class, responseContainer = "GameObject class")
    })
    @Path("/allobjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allObjects() {
            List<GameObject> gameObjects = this.productManager.getAllObjects();
            GenericEntity<List<GameObject>> entity = new GenericEntity<List<GameObject>>(gameObjects) {};
            return Response.status(201).entity(entity).build();
    }
}