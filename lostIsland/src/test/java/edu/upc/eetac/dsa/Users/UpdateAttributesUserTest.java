package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.GameObjectNotFoundException;
import edu.upc.eetac.dsa.exception.UserAlreadyConectedException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class UpdateAttributesUserTest {
    private ProductManager productManager;

    @Test
    public void updateAttributesUserDB() throws UserNotFoundException, UserAlreadyConectedException, GameObjectNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        this.productManager.modifyAttributes(5,2, true);
        Player player = this.productManager.logIn("Carlos", "Carlos");
        Assert.assertEquals(105, player.getCurrentHealth());
        this.productManager.clear();
    }
}
