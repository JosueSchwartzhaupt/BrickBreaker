package com.josuesch.world;

import com.josuesch.Game;
import com.josuesch.assets.Placeble;
import com.josuesch.entities.Block;
import com.josuesch.entities.Player;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static Tile[] tiles;
    private static final List<Placeble> solidTiles = new ArrayList<>();
    public static int WIDTH, HEIGHT;

    public World(String path)
    {
        solidTiles.clear();
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            WIDTH=map.getWidth();
            HEIGHT=map.getHeight();
            int[] pixels = new int[WIDTH* HEIGHT];
            tiles=new Tile[WIDTH*HEIGHT];
            map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
            for(int xx=0;xx<WIDTH;xx++)
            {
                for(int yy=0;yy<HEIGHT;yy++)
                {
                    switch (pixels[xx+(yy*WIDTH)])
                    {
                        case 0xFFFFFFFF://Wall
                            WallTile wall = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
                            tiles[xx+(yy*WIDTH)] = wall;
                            solidTiles.add(wall);
                            break;
                        case 0xFF000000://Floor
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                            break;
                        case 0xFF0026FF://Player
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                            Game.player.setX(xx*16 - Game.player.getWidth()/2.0);
                            Game.player.setY(yy*16);
                            break;
                        case 0xFF161D89://EasyBlock
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR, new Random().nextInt(4));
                            Game.entities.add(new Block(xx*16, yy*16, 16, 16, 1));
                            break;
                        case 0xFF1C9900:
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                            Game.entities.add(new Block(xx*16, yy*16, 16, 16, 2));
                            break;
                        case 0xFFE4BC00:
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                            Game.entities.add(new Block(xx*16, yy*16, 16, 16, 3));
                            break;
                        case 0xFFCE6E00:
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                            Game.entities.add(new Block(xx*16, yy*16, 16, 16, 4));
                            break;
                        case 0xFFCE0000:
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                            Game.entities.add(new Block(xx*16, yy*16, 16, 16, 5));
                            break;
                        default:
                            tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16,Tile.TILE_FLOOR,new Random().nextInt(4));
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g)
    {
        //TODO some old logic that needs trimming
        int xstart = 0 >> 4;
        int ystart = 0 >> 4;

        int xfinal = xstart + (Game.WIDTH >> 4) + 1;
        int yfinal = ystart + (Game.HEIGHT >> 4) + 1;

        for(int xx=xstart;xx<=xfinal;xx++)
        {
            for(int yy=ystart;yy<=yfinal;yy++)
            {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) continue;
                Tile tile = tiles[xx+(yy*WIDTH)];
                tile.render(g);
            }

        }

    }

    public static List<Placeble> getSolidTiles() {
        return solidTiles;
    }

    public static Tile[] getTiles() {
        return tiles;
    }
}
