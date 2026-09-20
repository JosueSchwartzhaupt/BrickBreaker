package com.josuesch.entities;

import com.josuesch.Game;
import com.josuesch.assets.Direction;
import com.josuesch.assets.HitboxComparator;
import com.josuesch.assets.Movable;
import com.josuesch.assets.Placeble;
import com.josuesch.world.WallTile;
import com.josuesch.world.World;

import java.util.Arrays;
import java.util.List;

import static com.josuesch.Game.entities;

public class Ball extends Entity implements Movable {
    private static final double NATURAL_SPEED = 3;

    private double angle;

    public Ball(double x, double y, int width, int height, double angle) {
        super(x, y, width, height);
        speed = NATURAL_SPEED;
        this.angle = angle;
    }

    public void move(){
        double relativeSpeed = speed;

        boolean willBounce = false;
        Direction wallToBounce = null;
        //TODO
        List<Placeble> list = new java.util.ArrayList<>(Arrays.stream(World.getTiles())
                .filter(tile -> tile instanceof WallTile)
                .map(tile -> (Placeble) tile) // Casts the filtered tiles to your interface
                .toList());
        list.addAll(entities);
        for (Placeble e : list) {
            if(e.equals(this)) continue;
           // if(e instanceof Ball) continue;
            double maxSpeed = HitboxComparator.getMaxSpeed(this, e);
            if(maxSpeed < relativeSpeed) {
                relativeSpeed = maxSpeed;
                wallToBounce = HitboxComparator.getClosestWall(this, e);

                // TODO metodo privado
                if(e instanceof Player){
                    double centerPlayer = e.getX() + e.getWidth() / 2.0;
                    double centerBall = x + width / 2.0;

                    double normalize =
                            (centerBall - centerPlayer) / (e.getWidth() / 2.0);

                    normalize = Math.max(-1, Math.min(1, normalize));

                    angle = Math.toRadians(270 + normalize * 45);
                }
                else {
                    willBounce = true;
                    if(e instanceof Block) ((Block) e).damage(1);
                }
                break;
            }
        }
        double dx = relativeSpeed * Math.cos(angle);
        double dy = relativeSpeed * Math.sin(angle);

        x += dx;
        y += dy;

        if(willBounce)bounce(wallToBounce);

        if(y> Game.HEIGHT + width + 10)dispawn();
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

    @Override
    public void dispawn(){
        super.dispawn();
        Game.balls.remove(this);
    }

}
