package edu.upc.eetac.dsa.service;

import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Stats;
import edu.upc.eetac.dsa.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value="/users", description = "Service of Autentication")
@Path("/users")
public class Users {
    final static Logger log = Logger.getLogger(Users.class.getName());

    private ProductManager productManager;

    public Users(){
        this.productManager = ProductManagerImpl.getInstance();
    }

    @PUT
    @ApiOperation(value = "update credentials", notes = "you can only modify your password introducing a new one")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/newcredentials")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCredentials(@PathParam("idUser") int idUser, String newpassword) {
        try {
            User user = this.productManager.getUser(idUser);
            String username = user.getUsername();
            String oldpassword = user.getPassword();
            this.productManager.modifyCredentials(username, oldpassword, newpassword);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @DELETE
    @ApiOperation(value = "delete account", notes = "delete an account given its credentials: username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/deleteaccount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("idUser") int idUser) {
        try {
            User user = this.productManager.getUser(idUser);
            String username = user.getUsername();
            String password = user.getPassword();
            this.productManager.deleteAccount(username, password);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get the stats of a player", notes = "get the points and the number of enemies killed of a player given its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Stats.class, responseContainer = "Stats class"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statsPlayer(@PathParam("idUser") int idUser) {
        try {
            Stats stats = this.productManager.getStatsOfAPlayer(idUser);
            log.info("Points: " +stats.getPoints());
            log.info("Enemies killed: " +stats.getEnemiesKilled());
            return Response.status(201).entity(stats).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get all objects of a player", notes = "get all objects of a player given its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = GameObject.class, responseContainer = "GameObject class"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response objectsPlayer(@PathParam("idUser") int idUser) {
        try {
            List<GameObject> gameObjects = this.productManager.getAllObjectsOfAPlayer(idUser);
            GenericEntity<List<GameObject>> entity = new GenericEntity<List<GameObject>>(gameObjects) {};
            return Response.status(201).entity(entity).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }
}