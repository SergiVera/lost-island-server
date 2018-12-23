package edu.upc.eetac.dsa.model;

public class User {
    //Attributes

    private String username;
    private String password;

    //Constructor

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Zero-argument constructor

    public User(){

    }

    //Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
