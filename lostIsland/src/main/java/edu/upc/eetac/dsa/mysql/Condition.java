package edu.upc.eetac.dsa.mysql;

public class Condition {

    //Attributes

    String s;
    String name;

    //Constructor

    public Condition(String s, String name) {
        this.s = s;
        this.name = name;
    }

    //Zero-argument constructor

    public Condition(){

    }

    //Getters and setters

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
