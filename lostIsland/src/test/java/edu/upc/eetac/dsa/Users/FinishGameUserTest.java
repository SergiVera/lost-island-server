package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.GameObjectNotFoundException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Enemy;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FinishGameUserTest {
    private ProductManager productManager;

    @Test
    public void deleteEnemyUserDB() throws UserNotFoundException, GameObjectNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.finishPlayerGame(1);
        List<Enemy> enemyList = this.productManager.getAllEnemiesOfAPlayer(1);
        Assert.assertEquals("Monkey", enemyList.get(0).getType());
        Assert.assertEquals(4, enemyList.get(0).getLife());
        this.productManager.clear();
    }
}
