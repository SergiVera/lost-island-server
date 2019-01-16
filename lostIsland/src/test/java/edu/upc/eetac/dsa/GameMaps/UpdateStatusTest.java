package edu.upc.eetac.dsa.GameMaps;

import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class UpdateStatusTest {
    private ProductManager productManager;

    @Test
    public void updateStatusUserDB() throws UserNotFoundException, UserAlreadyConectedException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.saveStatus(4, 1);
        Player player = this.productManager.logIn("Sergi", "Sergi");
        Assert.assertEquals(4, player.getCheckPoint());
        this.productManager.clear();
    }
}
