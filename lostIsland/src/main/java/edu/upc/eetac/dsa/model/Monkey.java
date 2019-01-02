package edu.upc.eetac.dsa.model;

public class Monkey extends Enemy {

    //Constructor

    public Monkey(int id, String type, int life, int map, int positionX, int positionY, int player_id) {
        super(id, type, life, map, positionX, positionY, player_id);
    }

    //Zero-argument constructor

    public Monkey() {
        super();
    }
}
