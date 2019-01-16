package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.GameObjectBoostDamageAlreadyInUseException;
import edu.upc.eetac.dsa.exception.GameObjectNotFoundException;
import edu.upc.eetac.dsa.exception.UserNoMoneyException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class InsertObjectUserTest {
    private ProductManager productManager;

    @Test
    public void insertObjectsUserDB() throws UserNotFoundException, GameObjectNotFoundException, UserNoMoneyException, GameObjectBoostDamageAlreadyInUseException {
        Player player;
        List<GameObject> gameObjectList;
        List<GameObject> gameObjectUserList;
        GameObject object = null;
        this.productManager = ProductManagerImpl.getInstance();
        player = this.productManager.getPlayer(1);
        gameObjectList = this.productManager.getAllObjects();

        for (GameObject gameObject : gameObjectList) {
            if (gameObject.getID() == 2) {
                object = gameObject;
            }
        }
        this.productManager.buyObject(1, 2, player.getPoints(), object.getCost());
        gameObjectUserList = this.productManager.getAllObjectsOfAPlayer(1);
        Assert.assertEquals("martillo", gameObjectUserList.get(1).getName());
        this.productManager.clear();
    }
}
