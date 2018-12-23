package edu.upc.eetac.dsa;

public class BoostLife extends GameObject{
    //Attributes
    private int boostPoints;

    //Constructor
    public BoostLife(int boostPoints) {
        this.boostPoints = boostPoints;
    }

    /*@Override
    public void modifyAttributes() {
        this.maxHealth += this.boostPoints;
    }*/
}
