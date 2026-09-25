package com.josuesch.entities;

import com.josuesch.Game;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public enum PowerUpType {
    MORE_BALLS(
            Game.spritesheet.getSprite(16 * 1 + 2, 16 * 1 + 2, 14, 14),
            (player) -> {
                var ball1 =
                        new Ball(
                                player.getX() + player.getWidth() / 2.0 - 2,
                                player.getY() - 5,
                                5,
                                5,
                                Math.toRadians(270));
                Game.addBall(ball1);
            });

    private final BufferedImage sprite;
    // TODO trocar estaticos em game para campos e passar por referencia pra todas entidades, ou
    // singleton dai aqui usar biconsumer
    private final Consumer<Player> effect;

    PowerUpType(BufferedImage sprite, Consumer<Player> effect) {
        this.sprite = sprite;
        this.effect = effect;
    }

    public void apply(Player player) {
        effect.accept(player);
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
