package com.josuesch.entities;

import com.josuesch.Game;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public enum PowerUpType {
    MORE_BALLS(
            Game.SPRITESHEET.getSprite(16 * 1 + 1, 16 * 1 + 1, 14, 14),
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
            }),
    DOUBLE_BALLS(
            Game.SPRITESHEET.getSprite(16 * 2 + 1, 16 * 1 + 1, 14, 14),
            (game, player) -> {
                game.addAllBalls(
                        game.getBalls().stream()
                                .map(
                                        b ->
                                                new Ball(
                                                        game,
                                                        b.getX(),
                                                        b.getY(),
                                                        b.getWidth(),
                                                        b.height,
                                                        b.getAngle() + Math.PI))
                                .toList());
            }),
    EXPAND_PADDLE(
            Game.SPRITESHEET.getSprite(16 * 3 + 1, 16 * 1 + 1, 14, 14),
            (game, player) -> {
                player.expandPaddle(4);
            }),
    INCREASE_SPEED(
            Game.SPRITESHEET.getSprite(16 * 4 + 1, 16 * 1 + 1, 14, 14),
            (game, player) -> {
                player.increaseSpeed(1);
            }),
    EXTRA_HEALTH(
            Game.SPRITESHEET.getSprite(16 * 5 + 1, 16 * 1 + 1, 14, 14),
            (game, player) -> {
                player.heal(1);
            }),
    DAMAGE(
            Game.SPRITESHEET.getSprite(16 * 0 + 1, 16 * 2 + 1, 14, 14),
            (game, player) -> {
                player.damage(1);
            });

    private final BufferedImage sprite;
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

    public static PowerUpType getRandomPowerUp() {
        PowerUpType[] powerUps = PowerUpType.values();
        return powerUps[Game.RANDOM.nextInt(powerUps.length)];
    }
}
