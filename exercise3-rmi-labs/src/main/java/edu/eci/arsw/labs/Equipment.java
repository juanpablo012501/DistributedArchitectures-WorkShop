package edu.eci.arsw.labs;

import java.io.Serializable;

public class Equipment implements Serializable {

    //attributes
    private String id;
    private String name;
    private String lab;
    private boolean available;

    //methods
    public Equipment(String id, String name, String lab) {
        this.id = id;
        this.name = name;
        this.lab = lab;
        this.available = true;
    }

    public String toText() {
        String free = (available) ? "Está disponible" : "No está disponible";
        return "El quipo " + id + ", con nombre " + name + ", del laboratorio " + lab + ": " + free;
    }

    //getters
    public String getId() {return id;}
    public String getName() {return name;}
    public String getLab() {return lab;}
    public boolean isAvailable() {return available;}

    //setter
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLab(String lab) {this.lab = lab;}
    public void setAvailable(boolean available) {this.available = available;}
}
