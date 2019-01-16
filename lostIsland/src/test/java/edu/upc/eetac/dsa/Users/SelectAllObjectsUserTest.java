package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SelectAllObjectsUserTest {
    private ProductManager productManager;

    @Test
    public void selectObjectsUserDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        List<GameObject> objectList = this.productManager.getAllObjectsOfAPlayer(1);
        Assert.assertEquals(20, objectList.get(0).getObjectPoints());
        Assert.assertEquals(30, objectList.get(1).getObjectPoints());
        this.productManager.clear();
    }
}
