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
            String text;
            String text2;
            String text3;

            if (game.isWin()) {
                text = "YOU WIN";
                text2 = "PRESS SPACE";
                text3 = "TO START A NEW GAME";
                g.setColor(new Color(0, 255, 0, 70));
            } else {
                text = "GAME OVER";
                text2 = "PRESS SPACE";
                text3 = "TO TRY AGAIN";
                g.setColor(new Color(255, 0, 0, 70));
            }

            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.setColor(Color.WHITE);

            drawCentered(g, text, Game.HEIGHT / 2 - 15);
            drawCentered(g, text2, Game.HEIGHT / 2);
            drawCentered(g, text3, Game.HEIGHT / 2 + 15);

        } else if (game.hasNotStarted()) {
            String text2;

            if (game.getPlayer().getHealth() < Player.MAX_HEALTH) {
                text2 = "TO SPAWN A NEW BALL";
            } else {
                text2 = "TO START";
            }

            g.setColor(new Color(0, 0, 0, 70));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

            g.setColor(Color.WHITE);
            drawCentered(g, "PRESS SPACE", Game.HEIGHT / 2 - 15);
            drawCentered(g, text2, Game.HEIGHT / 2);
        }
    }

    private void drawCentered(Graphics g, String text, int y) {
        int x = Game.WIDTH / 2 - g.getFontMetrics().stringWidth(text) / 2;
        g.drawString(text, x, y);
    }
}
