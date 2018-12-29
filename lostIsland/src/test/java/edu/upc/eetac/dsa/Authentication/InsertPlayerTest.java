package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class InsertPlayerTest {
    private ProductManager productManager;

    @Test
    public void insertPlayerDB() throws UserNotFoundException, UserAlreadyExistsException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.signUp("Carlos", "Carlos");
        Assert.assertEquals("100", this.productManager.logIn("Carlos", "Carlos").getCurrentHealth());
    }
}
