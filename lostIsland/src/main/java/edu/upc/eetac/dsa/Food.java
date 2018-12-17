package edu.upc.eetac.dsa;

public class Food extends GameObject {
    //Attributes
    private int foodpoints;

    //Empty constructor for JSON deserializer
    public Food(){

    }

    //Constructor
    public Food(int foodpoints) {
        this.foodpoints = foodpoints;
    }

    @Override
    public void modifyAttributes() {
        if (this.currentHealth < this.maxHealth)
            this.health += this.foodpoints;

        if(this.health > this.maxHealth) this.health = this.maxHealth;
    }
}
