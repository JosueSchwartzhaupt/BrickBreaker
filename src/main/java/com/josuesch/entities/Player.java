package com.josuesch.entities;

import com.josuesch.Game;
import com.josuesch.assets.HitboxComparator;
import com.josuesch.assets.Movable;

public class Player extends Entity implements Movable {
    private static final double NATURAL_SPEED = 3;

    private boolean right, up, left, down;

    private double speed;
    private double dx;
    private double dy;

    private int points;

    public Player(Game game, double x, double y, int width, int height) {
        super(game, x, y, width, height);
        speed = NATURAL_SPEED;
    }

    public void move() {
        int horizontal = (right ? 1 : 0) - (left ? 1 : 0);
        int vertical = (down ? 1 : 0) - (up ? 1 : 0);
        double mag = Math.sqrt(Math.pow(horizontal, 2) + Math.pow(vertical, 2));
        if (mag == 0) {
            mag = 1;
        }

        dx = speed * (horizontal / mag);
        dy = speed * (vertical / mag);

        double relativeSpeed = speed;
        var collision = HitboxComparator.processCollision(this, game.getCollidables());
        if (collision.isPresent()) relativeSpeed = collision.get().maxSpeed();
        dx = relativeSpeed * (horizontal / mag);
        dy = relativeSpeed * (vertical / mag);

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
        return response < 0 ? (response + (2 * Math.PI)) : response;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void resetPoints() {
        this.points = 0;
    }

    public void removePoints(int points) {
        this.points -= points;
    }

    @Override
    public boolean isColidable() {
        return true;
    }
}
