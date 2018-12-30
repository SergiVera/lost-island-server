package edu.upc.eetac.dsa.service;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.model.Stats;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
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

    @GET
    @ApiOperation(value = "get the stats of a user", notes = "get the points and the number of enemies killed of a user given its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Stats.class, responseContainer = "Stats class"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statsUser(@PathParam("idUser") int idUser) {
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
    @ApiOperation(value = "get all objects of a user", notes = "get all objects of a user given its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = GameObject.class, responseContainer = "GameObject class"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response objectsUser(@PathParam("idUser") int idUser) {
        try {
            List<GameObject> gameObjects = this.productManager.getAllObjectsOfAPlayer(idUser);
            GenericEntity<List<GameObject>> entity = new GenericEntity<List<GameObject>>(gameObjects) {};
            return Response.status(201).entity(entity).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get all the stats", notes = "get the points and the number of enemies killed of every player")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Stats.class, responseContainer = "Stats class")
    })
    @Path("/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allStats(){
        List<Stats> statsList = this.productManager.getStats();
        GenericEntity<List<Stats>> entity = new GenericEntity<List<Stats>>(statsList) {};
        return Response.status(201).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "update user points", notes = "modify the points of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/updatepoints/{points}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserStatus(@PathParam("idUser") int idUser,@PathParam("points") int points){
        try {
            this.productManager.updateUserPoints(idUser, points);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @PUT
    @ApiOperation(value = "update user enemieskilled", notes = "modify the number of killed enemies of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/updatekilledenemies/{enemieskilled}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserEnemiesKilled(@PathParam("idUser") int idUser,@PathParam("enemieskilled") int enemieskilled){
        try {
            this.productManager.updateUserEnemiesKilled(idUser, enemieskilled);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }
}