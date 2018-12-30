package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.User;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class UpdateConectedTest {
    private ProductManager productManager;

    @Test
    public void updateConectedUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.logOut(1);
        User user = this.productManager.getUser(1);
        Assert.assertEquals(false, user.getConected());
        this.productManager.clear();
    }
}
