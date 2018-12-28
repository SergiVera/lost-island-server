package edu.upc.eetac.dsa.model;

public class Food extends GameObject {

    //Constructor
    public Food(String type, String name, int objectPoints) {
        super(type, name, objectPoints);
    }

    //Zero-argument constructor
    public Food(){
        super();
    }

    @Override
    public void modifyAttributes() {

    }


   /* @Override
    public void modifyAttributes() {
        if (this.currentHealth < this.maxHealth)
            this.health += this.foodpoints;

        if(this.health > this.maxHealth) this.health = this.maxHealth;
    }*/
}
