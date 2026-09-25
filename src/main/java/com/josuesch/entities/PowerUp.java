package com.josuesch.entities;

import com.josuesch.Game;
import com.josuesch.assets.HitboxComparator;
import com.josuesch.assets.Movable;

public class PowerUp extends Entity implements Movable {
    private static final double NATURAL_SPEED = 1;
    private static final double FALLING_ANGLE = Math.toRadians(90);

    private PowerUpType type;

    public PowerUp(Game game, double x, double y, PowerUpType type) {
        super(game, x, y, 14, 14);
        this.type = type;
        sprite = type.getSprite();
    }

    @Override
    public void tick() {
        move();

        if (HitboxComparator.isColiding(this, game.getPlayer())) {
            type.apply(game, game.getPlayer());
            dispawn();
            return;
        }
        if (y > Game.HEIGHT + 20) {
            dispawn();
        }
    }

    private void move() {
        y += NATURAL_SPEED;
    }

    @Override
    public double getAngle() {
        return FALLING_ANGLE;
    }

    @Override
    public double getSpeed() {
        return NATURAL_SPEED;
    }
}
