package edu.upc.eetac.dsa.service;

import edu.upc.eetac.dsa.exception.*;
import edu.upc.eetac.dsa.model.Enemy;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.model.Player;
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
    @ApiOperation(value = "get attributes of a user", notes = "get the attributes of a user given its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Player.class, responseContainer = "Player class"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/attributes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attributesUser(@PathParam("idUser") int idUser) {
        try {
            Player player = this.productManager.getPlayer(idUser);
            return Response.status(201).entity(player).build();
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
    @ApiOperation(value = "get all remaining enemies of a user", notes = "get all remaining enemies of a user given its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Enemy.class, responseContainer = "Enemy class"),
            @ApiResponse(code = 404, message = "User doesn't exist")
    })
    @Path("/{idUser}/enemies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response enemiesUser(@PathParam("idUser") int idUser) {
        try {
            List<Enemy> enemies = this.productManager.getAllEnemiesOfAPlayer(idUser);
            GenericEntity<List<Enemy>> entity = new GenericEntity<List<Enemy>>(enemies) {};
            return Response.status(201).entity(entity).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @DELETE
    @ApiOperation(value = "delete an enemy of the user", notes = "delete an enemy from the enemiesList in case the user killed it")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist"),
            @ApiResponse(code = 403, message = "Enemy doesn't exist")
    })
    @Path("/{idUser}/delete-enemy/{idEnemy}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEnemyUser(@PathParam("idUser") int idUser, @PathParam("idEnemy") int idEnemy) {
        try {
            this.productManager.removeEnemyOfAPlayer(idUser, idEnemy);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        } catch (EnemyNotFoundException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }

    @PUT
    @ApiOperation(value = "update an enemy of the user", notes = "update an enemy from the enemiesList of the User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist"),
            @ApiResponse(code = 403, message = "Enemy doesn't exist")
    })
    @Path("/{idUser}/update-enemy/{idEnemy}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEnemyUser(@PathParam("idUser") int idUser, @PathParam("idEnemy") int idEnemy) {
        try {
            this.productManager.updateEnemyOfAPlayer(idUser, idEnemy, 4, 10, 12);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        } catch (EnemyNotFoundException e) {
            e.printStackTrace();
            return Response.status(403).build();
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

    @PUT
    @ApiOperation(value = "update attributes of the user", notes = "modify the maxHealth, the currentHealth or the life, depends on the object the user has grabbed")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist"),
            @ApiResponse(code = 403, message = "GameObject doesn't exist"),
    })
    @Path("/{idUser}/modifyattributes/{idGameObject}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserAttributes(@PathParam("idUser") int idUser,@PathParam("idGameObject") int idGameObject){
        try {
            this.productManager.modifyAttributes(idGameObject, idUser, true);
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        } catch (GameObjectNotFoundException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }

    @POST
    @ApiOperation(value = "buy an object from the shop", notes = "buy and object from the shop and upate the attributes of the user. You can buy only one object of type BoostDamage with this name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist"),
            @ApiResponse(code = 402, message = "You don't have enough points to buy this object"),
            @ApiResponse(code = 403, message = "GameObject doesn't exist"),
            @ApiResponse(code = 405, message = "The object that you want to add is already in use")
    })
    @Path("/{idUser}/buyobject/{idGameObject}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response buyGameObject(@PathParam("idUser") int idUser,@PathParam("idGameObject") int idGameObject){
        Player player;
        List<GameObject> gameObjectList;
        GameObject object = null;

        try {
            player = this.productManager.getPlayer(idUser);
            gameObjectList = this.productManager.getAllObjects();

            for (GameObject gameObject : gameObjectList) {
                if (gameObject.getID() == idGameObject) {
                    object = gameObject;
                }
            }
            this.productManager.buyObject(idUser, idGameObject, player.getPoints(), object.getCost());
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        } catch (UserNoMoneyException e) {
            e.printStackTrace();
            return Response.status(402).build();
        } catch (GameObjectNotFoundException e) {
            e.printStackTrace();
            return Response.status(403).build();
        } catch (GameObjectBoostDamageAlreadyInUseException e) {
            e.printStackTrace();
            return Response.status(405).build();
        }
    }

    @DELETE
    @ApiOperation(value = "sell an object from my Inventary", notes = "sel and object from my Inventary and upate the attributes of the user. When you sell an object, you only get the 50% of the points")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User doesn't exist"),
            @ApiResponse(code = 403, message = "GameObject doesn't exist")
    })
    @Path("/{idUser}/sellobject/{idGameObject}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sellGameObject(@PathParam("idUser") int idUser,@PathParam("idGameObject") int idGameObject){
        Player player;
        List<GameObject> gameObjectList;
        GameObject object = null;

        try {
            player = this.productManager.getPlayer(idUser);
            gameObjectList = this.productManager.getAllObjects();

            for (GameObject gameObject : gameObjectList) {
                if (gameObject.getID() == idGameObject) {
                    object = gameObject;
                }
            }
            this.productManager.sellObject(idUser, idGameObject, player.getPoints(), object.getCost());
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        } catch (GameObjectNotFoundException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }
}