package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class UpdateUserPoints {
    private ProductManager productManager;

    @Test
    public void updatePointsUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.updateUserPoints(1, 200);
        Player player = this.productManager.logIn("Sergi", "Sergi");
        Assert.assertEquals(200, player.getPoints());
        this.productManager.clear();
    }
}
