package com.josuesch.entities;

import com.josuesch.Game;

import java.awt.image.BufferedImage;

public class Block extends Entity{

    public static final BufferedImage BLOCK_1 = Game.spritesheet.getSprite(16*0, 16*0, 16, 16);
    public static final BufferedImage BLOCK_2 = Game.spritesheet.getSprite(16*1, 16*0, 16, 16);
    public static final BufferedImage BLOCK_3 = Game.spritesheet.getSprite(16*2, 16*0, 16, 16);
    public static final BufferedImage BLOCK_4 = Game.spritesheet.getSprite(16*3, 16*0, 16, 16);
    public static final BufferedImage BLOCK_5 = Game.spritesheet.getSprite(16*4, 16*0, 16, 16);

    private int health;

    public Block(double x, double y, int width, int height, int health) {
        super(x, y, width, height);
        this.health = health;
        updateSprite();
    }

    public void damage(int damage){
        health = Math.max(0, health - damage);
        Game.player.addPoints(100);
        updateSprite();
    }

    @Override
    public void tick() {
        if(health <= 0)this.dispawn();
    }

    //TODO, importar como lista e acessar indice
    private void updateSprite(){
        switch (health){
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
}
