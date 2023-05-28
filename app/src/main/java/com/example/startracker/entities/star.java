package com.example.startracker.entities;

public class star {
    String id;
    String name;
    String x;
    String y;
    String r;

    public star(){

    }

    public star(String id, String name, String x, String y, String r) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }
}
