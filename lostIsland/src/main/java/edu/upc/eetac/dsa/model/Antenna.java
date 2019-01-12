package edu.upc.eetac.dsa.model;

public class Antenna extends GameObject {
    //Constructor
    public Antenna(int id, String type, String name, int objectPoints, int cost) {
        super(id, type, name, objectPoints, cost);
    }

    //Zero-argument constructor
    public Antenna(){
        super();
    }

    @Override
    public Player modifyAttributesBuy(Player player) {
        return player;
    }

    @Override
    public Player modifyAttributesSell(Player player) {
        return player;
    }
}
