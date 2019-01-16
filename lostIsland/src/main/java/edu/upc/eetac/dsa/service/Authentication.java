package edu.upc.eetac.dsa.service;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.model.Credentials;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import edu.upc.eetac.dsa.exception.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
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
    @ApiOperation(value = "load the Profile", notes = "enter a username and password to login")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Player.class, responseContainer = "Player"),
            @ApiResponse(code = 404, message = "User doesn't exist"),
            @ApiResponse(code = 402, message = "User is already conected in other device")
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
        } catch (UserAlreadyConectedException e) {
            e.printStackTrace();
            return Response.status(402).build();
        }
    }

    @POST
    @ApiOperation(value = "create new Profile", notes = "enter a username and password to create a new account")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 402, message = "Username already exists")
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
            return Response.status(402).build();
        }
    }

    @PUT
    @ApiOperation(value = "update credentials", notes = "you can only modify your password introducing a new one")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/newcredentials")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCredentials(Credentials credentials){
        String username = credentials.getUsername();
        String oldpassword = credentials.getOldpassword();
        String newpassword = credentials.getNewpassword();

        try {
            this.productManager.modifyCredentials(username, oldpassword, newpassword);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "delete account", notes = "delete an account given its credentials: username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/deleteaccount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        try {
            this.productManager.deleteAccount(username, password);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @PUT
    @ApiOperation(value = "log out", notes = "log out a profile and change its status conected to false")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logOut(int idUser) {
        try {
            this.productManager.logOut(idUser);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }
}
