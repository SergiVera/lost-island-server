package edu.upc.eetac.dsa.model;

public class Boss extends Enemy {

    //Constructor

    public Boss(int id, String type, int life, int map, int positionX, int positionY, int player_id) {
        super(id, type, life, map, positionX, positionY, player_id);
    }

    //Zero-argument constructor

    public Boss() {
        super();
    }
}
