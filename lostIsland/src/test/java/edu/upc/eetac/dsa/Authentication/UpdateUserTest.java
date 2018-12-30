package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class UpdateUserTest {
    private ProductManager productManager;

    @Test
    public void updateUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.modifyCredentials("Sergi", "Martinez", "Vera");
        Assert.assertEquals("50", this.productManager.logIn("Sergi", "Vera").getCurrentHealth());
        this.productManager.clear();
    }
}
