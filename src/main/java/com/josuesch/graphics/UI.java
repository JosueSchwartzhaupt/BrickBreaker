package com.josuesch.graphics;

import com.josuesch.Game;

import java.awt.Color;
import java.awt.Graphics;

public class UI {

	
	public void render(Graphics g)
	{
		if(!Game.hasStarted) {
			g.setColor(new Color(0,0,0,50));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setColor(new Color(0,0,0));
			var text = "PRESS SPACE";
			g.drawString(text, Game.WIDTH/2 - (text.length()*9)/2, Game.HEIGHT/2 - 15);
			var text2 = "TO START";
			g.drawString(text2, Game.WIDTH/2 - (text2.length()*9)/2, Game.HEIGHT/2);
			return;
		}
		g.setColor(new Color(255,255,255));
		g.drawString("Points: "+ Game.player.getPoints(), 150 - 25, 14);

		if(Game.gameOver) {
			g.setColor(new Color(255,0,0,50));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setColor(new Color(0,0,0));

			var text = "GAME OVER";
			g.drawString(text, Game.WIDTH/2 - (text.length()*9)/2, Game.HEIGHT/2 - 15);
			var text2 = "PRESS SPACE";
			g.drawString(text2, Game.WIDTH/2 - (text2.length()*9)/2, Game.HEIGHT/2);
			var text3 = "TO TRY AGAIN";
			g.drawString(text3, Game.WIDTH/2 - (text3.length()*9)/2, Game.HEIGHT/2 + 15);
		}
	}
}
