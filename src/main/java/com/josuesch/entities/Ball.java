package com.josuesch.entities;

import com.josuesch.Game;
import com.josuesch.assets.Collision;
import com.josuesch.assets.Direction;
import com.josuesch.assets.HitboxComparator;
import com.josuesch.assets.Movable;
import com.josuesch.assets.Placeble;
import com.josuesch.world.WallTile;
import com.josuesch.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        double newAngle = angle;

        //TODO make better way to find colidables
        List<Placeble> list = new java.util.ArrayList<>(Arrays.stream(World.getTiles())
                .filter(tile -> tile instanceof WallTile)
                .map(tile -> (Placeble) tile) // Casts the filtered tiles to your interface
                .toList());
        list.addAll(entities);

        var collision = HitboxComparator.processCollision(this, list);
        if(collision.isPresent()){
            var coll = collision.get();
            relativeSpeed = coll.maxSpeed();

            newAngle = calculateBounceAngle(coll);

            var hit = coll.hit();
            if(hit instanceof Block) ((Block) hit).damage(1);
        }
        double dx = relativeSpeed * Math.cos(angle);
        double dy = relativeSpeed * Math.sin(angle);

        x += dx;
        y += dy;

        angle = newAngle;

        if(y> Game.HEIGHT + width + 10)dispawn();
    }

    private double calculateBounceAngle(Collision c){
        // Player Bounce
        if(c.hit() instanceof Player){
            var e = c.hit();
            double centerPlayer = e.getX() + e.getWidth() / 2.0;
            double centerBall = x + width / 2.0;

            double normalize =
                    (centerBall - centerPlayer) / (e.getWidth() / 2.0);

            normalize = Math.max(-1, Math.min(1, normalize));

            return Math.toRadians(270 + normalize * 45);
        }

        // Other bounce
        double dx = 1 * Math.cos(angle);
        double dy = 1 * Math.sin(angle);
        switch (c.direction()){
            case RIGHT, LEFT -> dx = -dx;
            case UP, DOWN -> dy = -dy;
        }

        double result = Math.atan2(dy, dx);
        if(result < 0)result += 2 * Math.PI;
        return result;
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
