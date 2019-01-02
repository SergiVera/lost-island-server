package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class SelectAttributesUserTest {
    private ProductManager productManager;

    @Test
    public void selectAttributesUserDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        Player player = this.productManager.getPlayer(1);
        Assert.assertEquals(6, player.getMaxHealth());
        Assert.assertEquals(0, player.getPoints());
        this.productManager.clear();
    }
}
