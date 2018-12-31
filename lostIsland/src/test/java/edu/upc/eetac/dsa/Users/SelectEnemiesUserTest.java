package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Enemy;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SelectEnemiesUserTest {
    private ProductManager productManager;

    @Test
    public void selectEnemiesUserDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        List<Enemy> objectList = this.productManager.getAllEnemiesOfAPlayer(1);
        Assert.assertEquals("1", objectList.get(0).getMap());
        Assert.assertEquals("2", objectList.get(1).getMap());
    }
}
