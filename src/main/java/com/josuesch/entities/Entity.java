package com.josuesch.entities;

import com.josuesch.assets.Drawable;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity implements Drawable {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

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

}
