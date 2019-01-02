package edu.upc.eetac.dsa.service;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value="/maps", description = "Service of Autentication")
@Path("/maps")
public class GameMaps {
    final static Logger log = Logger.getLogger(Authentication.class.getName());


    private ProductManager productManager;

    public GameMaps(){
        this.productManager = ProductManagerImpl.getInstance();
    }

    @PUT
    @ApiOperation(value = "update user status", notes = "change the map the user is in")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/savestatus/{idGameMap}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlayerStatus(@PathParam("idUser") int idUser,@PathParam("idGameMap") int idGameMap){
        try {
            this.productManager.saveStatus(idGameMap, idUser);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }
}