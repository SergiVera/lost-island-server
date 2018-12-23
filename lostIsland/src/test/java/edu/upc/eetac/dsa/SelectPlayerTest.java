package edu.upc.eetac.dsa;

import org.junit.Assert;
import org.junit.Test;

public class SelectPlayerTest {
    private ProductManager dao;

    @Test
    public void selectPlayerDB() throws UserNotFoundException {
        this.dao = ProductManagerImpl.getInstance();
        Assert.assertEquals("100",this.dao.logIn("Sergi", "Sergi").getMaxHealth());
    }
}
