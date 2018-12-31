package edu.upc.eetac.dsa.model;

public class Boss extends Enemy {

    //Constructor

    public Boss(int id, String type, int life, int map, int positionX, int positionY) {
        super(id, type, life, map, positionX, positionY);
    }

    //Zero-argument constructor

    public Boss() {
        super();
    }
}
