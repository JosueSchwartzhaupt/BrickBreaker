package com.josuesch.entities;

import com.josuesch.Game;
import com.josuesch.assets.Placeble;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity implements Placeble {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    protected BufferedImage sprite;

    protected final Game game;

    public Entity(Game game, double x, double y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, (int) this.getX(), (int) this.getY(), null);
            return;
        }
        g.setColor(new Color(255, 0, 0));
        g.fillRect((int) Math.round(x), (int) Math.round(y), width, height);
    }

    public void tick() {}

    public void dispawn() {
        game.queueRemoval(this);
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

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isColidable() {
        return false;
    }
}
