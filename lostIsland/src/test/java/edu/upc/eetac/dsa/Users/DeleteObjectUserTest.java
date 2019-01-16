package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.*;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DeleteObjectUserTest {
    private ProductManager productManager;

    @Test
    public void deleteObjectUserDB() throws UserNotFoundException, GameObjectNotFoundException {
        Player player;
        List<GameObject> gameObjectList;
        GameObject object = null;
        this.productManager = ProductManagerImpl.getInstance();
        player = this.productManager.getPlayer(1);
        gameObjectList = this.productManager.getAllObjects();

        for (GameObject gameObject : gameObjectList) {
            if (gameObject.getID() == 2) {
                object = gameObject;
            }
        }
        this.productManager.sellObject(1, 2, player.getPoints(), object.getCost());
        player = this.productManager.getPlayer(1);
        Assert.assertEquals(90, player.getPoints());
        this.productManager.clear();
    }
}
