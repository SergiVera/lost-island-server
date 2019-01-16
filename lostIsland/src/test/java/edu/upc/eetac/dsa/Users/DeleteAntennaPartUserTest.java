package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.GameObjectNotFoundException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DeleteAntennaPartUserTest {
    private ProductManager productManager;

    @Test
    public void deleteEnemyUserDB() throws UserNotFoundException, GameObjectNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.removeAntennaPartOfAPlayer(1, 10);
        List<GameObject> gameObjectList = this.productManager.getAllObjectsOfAPlayer(1);
        Assert.assertEquals("Reflector", gameObjectList.get(0).getType());
        this.productManager.clear();
    }
}
