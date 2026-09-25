package com.josuesch.graphics;

import com.josuesch.Game;
import com.josuesch.entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UI {

    public static BufferedImage FULL_HEART = Game.SPRITESHEET.getSprite(16 * 0, 16 * 9, 16, 16);
    public static BufferedImage EMPTY_HEART = Game.SPRITESHEET.getSprite(16 * 1, 16 * 9, 16, 16);

    private final Game game;

    public UI(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        var health = game.getPlayer().getHealth();
        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            if (i < health) g.drawImage(FULL_HEART, 4 + 11 * i, 0, null);
            else g.drawImage(EMPTY_HEART, 4 + 11 * i, 0, null);
        }
        g.setColor(new Color(255, 255, 255));
        g.drawString("Points: " + Game.player.getPoints(), 150 - 25, 14);

        if (game.isGameOver()) {
            g.setColor(new Color(255, 0, 0, 70));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.setColor(new Color(255, 255, 255));

            var text = "GAME OVER";
            g.drawString(text, Game.WIDTH / 2 - (text.length() * 9) / 2, Game.HEIGHT / 2 - 15);
            var text2 = "PRESS SPACE";
            g.drawString(text2, Game.WIDTH / 2 - (text2.length() * 9) / 2, Game.HEIGHT / 2);
            var text3 = "TO TRY AGAIN";
            g.drawString(text3, Game.WIDTH / 2 - (text3.length() * 9) / 2, Game.HEIGHT / 2 + 15);
        } else if (game.hasNotStarted()) {
            g.setColor(new Color(0, 0, 0, 70));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.setColor(new Color(255, 255, 255));
            var text = "PRESS SPACE";
            g.drawString(text, Game.WIDTH / 2 - (text.length() * 9) / 2, Game.HEIGHT / 2 - 15);
            var text2 = "TO START";
            g.drawString(text2, Game.WIDTH / 2 - (text2.length() * 9) / 2, Game.HEIGHT / 2);
        }
    }
}
