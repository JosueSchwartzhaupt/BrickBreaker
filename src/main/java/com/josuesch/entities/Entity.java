package com.josuesch.entities;

import com.josuesch.assets.Drawable;
import com.josuesch.assets.Placeble;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity implements Drawable, Placeble {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    protected int speed;
    protected double dx;
    protected double dy;


    public Entity(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(255,0,0));
        g.fillRect((int)Math.round(x), (int)Math.round(y), width, height);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        this.draw(g);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
