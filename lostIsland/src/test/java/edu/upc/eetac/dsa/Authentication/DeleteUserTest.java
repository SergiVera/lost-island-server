package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class DeleteUserTest {
    private ProductManager productManager;

    @Test
    public void deleteUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.deleteAccount("Carlos", "Carlos");
        Player player = this.productManager.logIn("Carlos", "Carlos");
        Assert.assertNull(player);
        this.productManager.clear();
    }
}
