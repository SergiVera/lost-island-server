package edu.upc.eetac.dsa.model;

public abstract class Enemy {

    //Attributes

    int ID;
    String type;
    int life;
    int map;
    int positionX;
    int positionY;
    int player_id;

    //Constructor

    public Enemy(int ID, String type, int life, int map, int positionX, int positionY, int player_id) {
        this.ID = ID;
        this.type = type;
        this.life = life;
        this.map = map;
        this.positionX = positionX;
        this.positionY = positionY;
        this.player_id = player_id;
    }


    //Zero-argument constructor

    public Enemy(){

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

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public abstract Enemy modifyAttributes(Enemy enemy);
}
