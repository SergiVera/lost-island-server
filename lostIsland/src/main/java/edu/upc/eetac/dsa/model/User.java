package edu.upc.eetac.dsa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

public class User {
    //Attributes

    private String username;
    private String password;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private boolean conected;

    //Constructor

    public User(String username, String password, boolean conected) {
        this.username = username;
        this.password = password;
        this.conected = conected;
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

    public boolean getConected() {
        return conected;
    }

    public void setConected(boolean conected) {
        this.conected = conected;
    }
}
