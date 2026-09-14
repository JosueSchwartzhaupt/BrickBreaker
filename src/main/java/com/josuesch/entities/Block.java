package com.josuesch.entities;

public class Block extends Entity{
    private int health;

    public Block(double x, double y, int width, int height, int health) {
        super(x, y, width, height);
        this.health = health;
    }

    public void damage(int damage){
        health = Math.max(0, health - damage);
    }

    @Override
    public void tick() {
        if(health <= 0)this.dispawn();
    }
}
