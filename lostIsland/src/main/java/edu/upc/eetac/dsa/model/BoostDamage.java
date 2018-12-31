package edu.upc.eetac.dsa.model;

public class BoostDamage extends GameObject {

    //Constructor
    public BoostDamage(int id, String type, String name, int objectPoints, int cost) {
        super(id, type, name, objectPoints, cost);
    }

    //Zero-argument constructor
    public BoostDamage(){
        super();
    }

    @Override
    public Player modifyAttributes(Player player) {
        int attackPoints = player.getAttack();
        attackPoints += this.objectPoints;
        player.setAttack(attackPoints);
        return player;
    }
}
