package edu.upc.eetac.dsa.model;

public class BoostLife extends GameObject {

    //Constructor
    public BoostLife(int id, String type, String name, int objectPoints, int cost) {
        super(id, type, name, objectPoints, cost);
    }

    //Zero-argument constructor
    public BoostLife(){
        super();
    }

    @Override
    public Player modifyAttributesBuy(Player player) {
        int maxHealth = player.getMaxHealth();
        maxHealth += this.objectPoints;
        player.setMaxHealth(maxHealth);
        return player;
    }

    @Override
    public Player modifyAttributesSell(Player player) {
        int maxHealth = player.getMaxHealth();
        maxHealth -= this.objectPoints;
        player.setMaxHealth(maxHealth);
        return player;
    }
}
