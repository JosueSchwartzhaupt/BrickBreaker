package com.josuesch.entities;

import com.josuesch.assets.HitboxComparator;
import com.josuesch.assets.Movable;

import java.awt.Graphics;

import static com.josuesch.Game.entities;

public class Player extends Entity implements Movable {
    private static final int NATURAL_SPEED = 2;

    private boolean right,up,left,down;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
        speed = NATURAL_SPEED;
    }

    public void move(){
        int horizontal = (right? 1:0) - (left? 1:0);
        int vertical = (down? 1:0) - (up? 1:0);
        double mag = Math.sqrt(Math.pow(horizontal, 2)+Math.pow(vertical, 2));
        if(mag==0) {
            mag=1;
        }

        dx = speed * (horizontal/mag);
        dy = speed * (vertical/mag);

        double relativeSpeed = speed;
        //TODO
        for (Entity e : entities) {
            if(e.equals(this)) continue;
            //if(e instanceof Ball) continue;
            relativeSpeed = Math.min(relativeSpeed,HitboxComparator.getMaxSpeed(this, e));
        }
        dx = relativeSpeed * (horizontal/mag);
        dy = relativeSpeed * (vertical/mag);

        x += dx;
        y += dy;
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

    @Override
    public double getAngle() {
        double response = Math.atan2(dy, dx);
        return response < 0? (response + (2 * Math.PI)): response;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

}
