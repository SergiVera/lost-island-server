package edu.upc.eetac.dsa.Users;

import edu.upc.eetac.dsa.model.Stats;
import edu.upc.eetac.dsa.mysql.ProductManager;
import edu.upc.eetac.dsa.mysql.ProductManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SelectAllStatsTest {
    private ProductManager productManager;

    @Test
    public void selectAllStatsDB(){
        this.productManager = ProductManagerImpl.getInstance();
        List<Stats> statsList = this.productManager.getStats();
        Assert.assertEquals(25, statsList.get(0).getPoints());
        Assert.assertEquals("Carlos", statsList.get(1).getUsername());
        this.productManager.clear();
    }
}
