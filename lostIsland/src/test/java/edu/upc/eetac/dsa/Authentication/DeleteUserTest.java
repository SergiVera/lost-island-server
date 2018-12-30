package edu.upc.eetac.dsa.Authentication;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Test;

public class DeleteUserTest {
    private ProductManager productManager;

    @Test
    public void deleteUserDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.deleteAccount("Carlos", "Carlos");
        this.productManager.clear();
    }
}
