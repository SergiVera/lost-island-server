package edu.upc.eetac.dsa;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Test;

public class DeletePlayerTest {
    private ProductManager productManager;

    @Test
    public void deletePlayerDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.deleteAccount("Carlos", "Carlos");
        this.productManager.clear();
    }
}
