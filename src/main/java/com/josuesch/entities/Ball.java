package com.josuesch.entities;

import com.josuesch.Game;
import com.josuesch.assets.Direction;
import com.josuesch.assets.HitboxComparator;
import com.josuesch.assets.Movable;

import static com.josuesch.Game.entities;

public class Ball extends Entity implements Movable {
    private static final int NATURAL_SPEED = 1;

    private int speed = NATURAL_SPEED;
    private double angle;

    public Ball(double x, double y, int width, int height, double angle) {
        super(x, y, width, height);
        this.angle = angle;
    }

    public void move(){
        double relativeSpeed = speed;

        boolean willBounce = false;
        Direction wallToBounce = null;
        //TODO
        for (Entity e : entities) {
            if(e.equals(this)) continue;
           // if(e instanceof Ball) continue;
            double maxSpeed = HitboxComparator.getMaxSpeed(this, e);
            if(maxSpeed < relativeSpeed) {
                relativeSpeed = maxSpeed;
                wallToBounce = HitboxComparator.getClosestWall(this, e);
                willBounce = true;
            }
        }
        double dx = relativeSpeed * Math.cos(angle);
        double dy = relativeSpeed * Math.sin(angle);

        x += dx;
        y += dy;

        if(willBounce)bounce(wallToBounce);
    }
    private void bounce(Direction d){
        double dx = 1 * Math.cos(angle);
        double dy = 1 * Math.sin(angle);
        switch (d){
            case RIGHT, LEFT -> dx = -dx;
            case UP, DOWN -> dy = -dy;
        }
        //System.out.println(d);
        //System.out.println(angle);
        angle = Math.atan2(dy, dx);
        if(angle < 0)angle += 2 * Math.PI;
        //System.out.println(angle);
    }

    @Override
    public void tick() {
        move();
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

}
