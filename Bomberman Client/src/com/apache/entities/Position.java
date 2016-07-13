package com.apache.entities;

public class Position {

    private float x, y;

    public Position(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

}
