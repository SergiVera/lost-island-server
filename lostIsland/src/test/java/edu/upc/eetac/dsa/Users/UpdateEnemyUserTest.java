package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.EnemyNotFoundException;
import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Enemy;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UpdateEnemyUserTest {
    private ProductManager productManager;

    @Test
    public void updateEnemyUserDB() throws UserNotFoundException, UserAlreadyConectedException, EnemyNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.updateEnemyOfAPlayer(1, 2, 4, 10, 12);
        List<Enemy> enemyList = this.productManager.getAllEnemiesOfAPlayer(1);
        Assert.assertEquals(4, enemyList.get(1).getLife());
        this.productManager.clear();
    }
}
