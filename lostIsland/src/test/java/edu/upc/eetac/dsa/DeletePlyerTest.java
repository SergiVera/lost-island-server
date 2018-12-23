package edu.upc.eetac.dsa;

import org.junit.Test;

public class DeletePlyerTest {
    private ProductManager productManager;

    @Test
    public void deletePlayerDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.deleteAccount("Sergi", "Sergi");
        this.productManager.clear();
    }
}
