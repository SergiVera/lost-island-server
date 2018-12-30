package edu.upc.eetac.dsa.model;

public class Credentials {

    //Attributes

    String username;
    String oldpassword;
    String newpassword;

    //Constructor

    public Credentials(String username, String oldpassword, String newpassword) {
        this.username = username;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }

    //Zero-argument constructor

    public Credentials(){

    }

    //Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
