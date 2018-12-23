package edu.upc.eetac.dsa.services;

import edu.upc.eetac.dsa.ProductManager;
import edu.upc.eetac.dsa.ProductManagerImpl;
import edu.upc.eetac.dsa.UserAlreadyExistsException;
import edu.upc.eetac.dsa.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value="/auth", description = "Service of User")
@Path("/auth")
public class Authentication {
    final static Logger log = Logger.getLogger(Authentication.class.getName());

    private ProductManager productManager;

    public Authentication(){
        this.productManager = ProductManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "load the Profile", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Player.class, responseContainer = "Player"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            Player player = this.productManager.logIn(username, password);
            return Response.status(201).entity(player).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "create new Profile", notes = "x")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Username already exists")
    })
    @Path("/sign-up")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUp(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            this.productManager.signUp(username, password);
            return Response.status(201).build();
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }
}
