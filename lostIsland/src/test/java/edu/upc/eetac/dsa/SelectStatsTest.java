package edu.upc.eetac.dsa;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

public class SelectStatsTest {
    private ProductManager dao;

    @Test
    public void selectPlayerDB() throws UserNotFoundException {
        this.dao = ProductManagerImpl.getInstance();
        Assert.assertEquals("25",this.dao.getStatsOfAPlayer(1).getPoints());
    }
}
