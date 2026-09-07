package com.josuesch.entities;

import java.awt.Graphics;

public class Player extends Entity{
    private static final int NATURAL_SPEED = 2;

    private int speed = NATURAL_SPEED;
    private double dx;
    private double dy;

    private boolean right,up,left,down;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public void move(){
        int horizontal = (right? 1:0) - (left? 1:0);
        int vertical = (right? 1:0) - (left? 1:0);
        double mag = Math.sqrt(Math.pow(horizontal, 2)+Math.pow(vertical, 2));
        if(mag==0) {
            mag=1;
        }

        dx = speed * (horizontal/mag);
        dy = speed * (vertical/mag);

        x += dx;
        x += dy;
    }

    @Override
    public void tick() {
        move();
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
