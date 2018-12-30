package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class UpdateUserEnemiesKilled {
    private ProductManager productManager;

    @Test
    public void updateEnemiesKilledUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.updateUserEnemiesKilled(1, 9);
        Player player = this.productManager.logIn("Sergi", "Sergi");
        Assert.assertEquals("9", player.getEnemiesKilled());
        this.productManager.clear();
    }
}
