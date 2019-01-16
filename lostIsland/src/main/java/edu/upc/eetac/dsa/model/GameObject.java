package edu.upc.eetac.dsa.model;

public abstract class GameObject{

    //Attributes

    int ID;
    String type;
    String name;
    int objectPoints;
    int cost;

    //Constructor

    public GameObject(int ID, String type, String name, int objectPoints, int cost) {
        this.ID = ID;
        this.type = type;
        this.name = name;
        this.objectPoints = objectPoints;
        this.cost = cost;
    }

    //Zero-argument constructor

    public GameObject(){

    }

    //Getters and setters


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    //Method that modifies the attributes of the player when user buys an object

    public abstract Player modifyAttributesBuy(Player player);

    //Method that modifies the attributes of the player when user sells an object

    public abstract Player modifyAttributesSell(Player player);
}
