package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.exception.GameObjectNotFoundException;
import edu.upc.eetac.dsa.exception.UserNoMoneyException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.mysql.ProductManager;
import org.junit.Test;

public class InsertObjectUserTest {
    private ProductManager productManager;

    @Test
    public void insertObjectsUserDB() throws UserNotFoundException, GameObjectNotFoundException, UserNoMoneyException {
        /*this.productManager = ProductManagerImpl.getInstance();
        this.productManager.addObject(1, "BoostDamage", "martillo");
        Assert.assertEquals("20", objectList.get(0).getObjectPoints());
        Assert.assertEquals("30", objectList.get(1).getObjectPoints());*/
    }
}
