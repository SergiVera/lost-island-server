package edu.upc.eetac.dsa.model;

public class Food extends GameObject {

    //Constructor
    public Food(int id, String type, String name, int objectPoints, int cost) {
        super(id, type, name, objectPoints, cost);
    }

    //Zero-argument constructor
    public Food(){
        super();
    }

    @Override
    public Player modifyAttributesBuy(Player player) {
        int maxHealth = player.getMaxHealth();
        int currentHealth = player.getCurrentHealth();
        if(currentHealth < maxHealth) {
            currentHealth += this.objectPoints;
            if(currentHealth >= maxHealth){
                currentHealth = maxHealth;
            }
        }
        player.setCurrentHealth(currentHealth);
        return player;
    }

    @Override
    public Player modifyAttributesSell(Player player) {
        int maxHealth = player.getMaxHealth();
        int currentHealth = player.getCurrentHealth();
        if(currentHealth < maxHealth){
            currentHealth -= this.objectPoints;
            if(currentHealth < 1){
                currentHealth = 1;
            }
        }
        player.setCurrentHealth(currentHealth);
        return player;
    }
}
