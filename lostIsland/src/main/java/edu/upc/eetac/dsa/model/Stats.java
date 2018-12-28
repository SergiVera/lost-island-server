package edu.upc.eetac.dsa.model;

public class Stats {
    //Attributes
    private int points;
    private int enemiesKilled;

    //Constructor

    public Stats(int points, int enemiesKilled) {
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
}
