package edu.upc.eetac.dsa.model;

public abstract class GameObject{

    //Attributes

    String type;
    String name;
    int objectPoints;

    //Constructor

    public GameObject(String type, String name, int objectPoints) {
        this.type = type;
        this.name = name;
        this.objectPoints = objectPoints;
    }

    //Zero-argument constructor

    public GameObject(){

    }

    //Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectPoints() {
        return objectPoints;
    }

    public void setObjectPoints(int objectPoints) {
        this.objectPoints = objectPoints;
    }

    //Method that modifies the attributes of the player

    public abstract void modifyAttributes();
}
