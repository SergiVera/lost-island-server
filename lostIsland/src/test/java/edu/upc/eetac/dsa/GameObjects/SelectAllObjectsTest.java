package edu.upc.eetac.dsa.GameObjects;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SelectAllObjectsTest {
    private ProductManager productManager;

    @Test
    public void selectPlayerDB() {
        this.productManager = ProductManagerImpl.getInstance();
        List<GameObject> objectList = this.productManager.getAllObjects();
        Assert.assertEquals("20", objectList.get(0).getObjectPoints());
        Assert.assertEquals("30", objectList.get(1).getObjectPoints());
    }
}
