package edu.upc.eetac.dsa;

import org.junit.Assert;
import org.junit.Test;

public class InsertPlayerTest {
    private ProductManager dao;

    @Test
    public void insertPlayerDB() throws UserNotFoundException, UserAlreadyExistsException {
        this.dao = ProductManagerImpl.getInstance();
        this.dao.signUp("Carlos", "Carlos");
        Assert.assertEquals("100", this.dao.logIn("Carlos", "Carlos").getCurrentHealth());
    }
}
