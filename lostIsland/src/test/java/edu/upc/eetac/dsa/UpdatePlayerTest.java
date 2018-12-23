package edu.upc.eetac.dsa;

import org.junit.Assert;
import org.junit.Test;

public class UpdatePlayerTest {
    private ProductManager productManager;

    @Test
    public void updatePlayerDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.modifyCredentials("Sergi", "Martinez", "Vera");
        Assert.assertEquals("50", this.productManager.logIn("Sergi", "Vera").getCurrentHealth());
        this.productManager.clear();
    }
}
