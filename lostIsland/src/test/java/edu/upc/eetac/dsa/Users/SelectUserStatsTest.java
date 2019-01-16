package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class SelectUserStatsTest {
    private ProductManager productManager;

    @Test
    public void selectStatsUserDB() throws UserNotFoundException {
        this.productManager = ProductManagerImpl.getInstance();
        Assert.assertEquals(25,this.productManager.getStatsOfAPlayer(1).getPoints());
        this.productManager.clear();
    }
}
