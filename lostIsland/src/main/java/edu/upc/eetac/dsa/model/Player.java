package edu.upc.eetac.dsa.model;

public class Player {
    //Attributes

    private int currentHealth;
    private int maxHealth;
    private int attack;
    private int checkPoint;
    private int points;
    private int enemiesKilled;
    private int user_id;

    //Constructor

    public Player(int currentHealth, int maxHealth, int attack, int checkPoint, int points, int enemiesKilled, int user_id) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.checkPoint = checkPoint;
        this.points = points;
        this.enemiesKilled = enemiesKilled;
        this.user_id = user_id;
    }


    //Zero-argument constructor

    public Player(){

    }

    //Getters and setters


    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(int checkPoint) {
        this.checkPoint = checkPoint;
    }

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
