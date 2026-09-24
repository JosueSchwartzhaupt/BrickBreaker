package com.josuesch.world;


import com.josuesch.Game;
import com.josuesch.assets.Placeble;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Tile implements Placeble {

	public static final BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(16*1, 16*4, 16, 16);
	public static final BufferedImage TILE_WALL = Game.spritesheet.getSprite(16*0, 16*4, 16, 16);
	public static final int SIZE = 16;
	
	protected BufferedImage sprite;
	protected int x,y;
	
	public Tile(int x, int y, BufferedImage sprite)
	{
		this.x=x;
		this.y=y;
		this.sprite=sprite;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x,y,null);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return SIZE;
	}

	@Override
	public int getHeight() {
		return SIZE;
	}
}
