package com.josuesch.world;



import java.awt.image.BufferedImage;

public class FloorTile extends Tile
{
	public FloorTile(int x, int y, BufferedImage sprite,int rotation) 
	{
		super(x, y, sprite);
	}

	@Override
	public boolean isColidable() {
		return false;
	}
}
