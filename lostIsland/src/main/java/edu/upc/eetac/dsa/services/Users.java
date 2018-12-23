package edu.upc.eetac.dsa.services;

import edu.upc.eetac.dsa.ProductManager;
import edu.upc.eetac.dsa.ProductManagerImpl;
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
}