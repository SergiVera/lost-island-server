package edu.upc.eetac.dsa.model;

public abstract class Enemy {

    //Attributes

    int id;
    String type;
    int life;
    int map;
    int positionX;
    int positionY;

    //Constructor

    public Enemy(int id, String type, int life, int map, int positionX, int positionY) {
        this.id = id;
        this.type = type;
        this.life = life;
        this.map = map;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Zero-argument constructor

    public Enemy(){

    }

    //Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
