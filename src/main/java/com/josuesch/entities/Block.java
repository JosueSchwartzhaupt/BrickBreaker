package com.josuesch.entities;

import com.josuesch.Game;
import java.awt.image.BufferedImage;

public class Block extends Entity {

    public static final BufferedImage BLOCK_1 = Game.SPRITESHEET.getSprite(16 * 0, 16 * 0, 16, 16);
    public static final BufferedImage BLOCK_2 = Game.SPRITESHEET.getSprite(16 * 1, 16 * 0, 16, 16);
    public static final BufferedImage BLOCK_3 = Game.SPRITESHEET.getSprite(16 * 2, 16 * 0, 16, 16);
    public static final BufferedImage BLOCK_4 = Game.SPRITESHEET.getSprite(16 * 3, 16 * 0, 16, 16);
    public static final BufferedImage BLOCK_5 = Game.SPRITESHEET.getSprite(16 * 4, 16 * 0, 16, 16);

    private int health;

    public Block(Game game, double x, double y, int width, int height, int health) {
        super(game, x, y, width, height);
        this.health = health;
        updateSprite();
    }

    public void damage(int damage) {
        health = Math.max(0, health - damage);
        Game.player.addPoints(100);
        updateSprite();
    }

    @Override
    public void tick() {
        if (health <= 0) {
            this.dispawn();
            if ((Game.RANDOM.nextInt(4) + 1) % 4 == 0)
                game.getEntities().add(new PowerUp(game, x + 2, y + 2, PowerUpType.MORE_BALLS));
        }
    }

    // TODO, importar como lista e acessar indice
    private void updateSprite() {
        switch (health) {
            case 1:
                sprite = BLOCK_1;
                break;
            case 2:
                sprite = BLOCK_2;
                break;
            case 3:
                sprite = BLOCK_3;
                break;
            case 4:
                sprite = BLOCK_4;
                break;
            case 5:
                sprite = BLOCK_5;
                break;
        }
    }

    @Override
    public boolean isColidable() {
        return true;
    }
}
