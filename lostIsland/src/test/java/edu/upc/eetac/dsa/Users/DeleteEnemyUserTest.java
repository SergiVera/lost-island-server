package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.EnemyNotFoundException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Enemy;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DeleteEnemyUserTest {
    private ProductManager productManager;

    @Test
    public void deleteEnemyUserDB() throws UserNotFoundException, EnemyNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.removeEnemyOfAPlayer(1, 1);
        List<Enemy> enemyList = this.productManager.getAllEnemiesOfAPlayer(1);
        Assert.assertEquals("Monkey", enemyList.get(0).getType());
        Assert.assertEquals(2, enemyList.get(0).getLife());
        Assert.assertEquals(2, enemyList.get(0).getMap());
        Assert.assertEquals(3, enemyList.get(0).getPositionX());
        Assert.assertEquals(9, enemyList.get(0).getPositionY());
        this.productManager.clear();
    }
}
