package edu.upc.eetac.dsa.model;

public class Stats {
    //Attributes

    private String username;
    private int points;
    private int enemiesKilled;

    //Constructor

    public Stats(String username, int points, int enemiesKilled) {
        this.username = username;
        this.points = points;
        this.enemiesKilled = enemiesKilled;
    }

    //Zero-argument constructor

    public Stats(){

    }

    //Getters and setters


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
