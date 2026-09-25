package com.josuesch.entities;

import com.josuesch.Game;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public enum PowerUpType {
    MORE_BALLS(
            Game.SPRITESHEET.getSprite(16 * 1 + 2, 16 * 1 + 2, 14, 14),
            (game, player) -> {
                var ball1 =
                        new Ball(
                                game,
                                player.getX() + player.getWidth() / 2.0 - 2,
                                player.getY() - 5,
                                5,
                                5,
                                Math.toRadians(270));
                game.addBall(ball1);
            });

    private final BufferedImage sprite;
    // TODO trocar estaticos em game para campos e passar por referencia pra todas entidades, ou
    // singleton dai aqui usar biconsumer
    private final BiConsumer<Game, Player> effect;

    PowerUpType(BufferedImage sprite, BiConsumer<Game, Player> effect) {
        this.sprite = sprite;
        this.effect = effect;
    }

    public void apply(Game game, Player player) {
        effect.accept(game, player);
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
