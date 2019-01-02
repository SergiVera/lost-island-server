package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class SelectUserTest {
    private ProductManager productManager;

    @Test
    public void selectUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        Assert.assertEquals(100,this.productManager.logIn("Sergi", "Sergi").getMaxHealth());
        this.productManager.clear();
    }
}
