package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class SelectPlayerTest {
    private ProductManager productManager;

    @Test
    public void selectPlayerDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        Assert.assertEquals("100",this.productManager.logIn("Sergi", "Sergi").getMaxHealth());
    }
}
