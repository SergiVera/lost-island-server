package edu.upc.eetac.dsa.model;

public class Players_Gameobjects {

    //Attributes

    int player_id;
    int gameObject_idGameObject;

    //Constructor

    public Players_Gameobjects(int player_id, int gameObject_idGameObject) {
        this.player_id = player_id;
        this.gameObject_idGameObject = gameObject_idGameObject;
    }

    //Zero-argument constructor

    public Players_Gameobjects(){

    }

    //Getters and setters

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getGameObject_idGameObject() {
        return gameObject_idGameObject;
    }

    public void setGameObject_idGameObject(int gameObject_idGameObject) {
        this.gameObject_idGameObject = gameObject_idGameObject;
    }
}
