package edu.upc.eetac.dsa.GameMaps;

import edu.upc.eetac.dsa.model.Enemy;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SelectAllEnemiesTest {
    private ProductManager productManager;

    @Test
    public void selectEnemiesDB() {
        this.productManager = ProductManagerImpl.getInstance();
        List<Enemy> objectList = this.productManager.getAllEnemies();
        Assert.assertEquals("20", objectList.get(0).getLife());
        Assert.assertEquals("50", objectList.get(1).getLife());
        this.productManager.clear();
    }
}
