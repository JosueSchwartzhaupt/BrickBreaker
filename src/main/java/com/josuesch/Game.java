package com.josuesch;


import com.josuesch.entities.Ball;
import com.josuesch.entities.Entity;
import com.josuesch.entities.Player;
import com.josuesch.entities.Wall;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {

    private Thread thread;
    private boolean isRunning = false;
    private static JFrame frame;

    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;
    public static final int SCALE = 3;

    public static Random random;

    private BufferedImage image;

    public static Player player;

    public static List<Entity> entities;

    public Game() {
        random = new Random();
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Cria a tela de fundo onde vamos desenhar tudo;
        entities = new ArrayList<Entity>();
        player = new Player(100,180,32,8);
        entities.add(player);

        //TODO
        entities.add(new Wall(-5,-5,10,HEIGHT));
        entities.add(new Wall(-5,-5,WIDTH,10));
        entities.add(new Wall(-5+ HEIGHT,0,10,HEIGHT));
        entities.add(new Wall(0,-5+ WIDTH,WIDTH,10));
        for (int i = 0; i < 20; i++) {
            entities.add(new Ball(10+10*i,10,5,5, Math.toRadians(Game.random.nextInt(360))));
        }
    }

    public void initFrame()
    {
        frame = new JFrame("BrickBreaker");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop()
    {
        isRunning=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        int frames = 0;
        double timer= System.currentTimeMillis();
        requestFocus();
        while(isRunning)
        {

            long now=System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime= now;
            if(delta>=1)
            {
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis()-timer>=1000)
            {
               // System.out.println("FPS: "+frames);
                frames=0;
                timer+=1000;
            }
        }
        stop();
    }


    public void tick()
    {
        for (Entity e : entities) {
            e.tick();
        }
    }

    public void render()
    {
        BufferStrategy bs = this.getBufferStrategy();//pega a que ja tem
        if(bs == null)
        {
            this.createBufferStrategy(3);//se nao tem cria uma nova
            return;
        }
        //desenha na tela de fundo
        Graphics g = image.getGraphics();

        //preenche a ultima tela, se nao ficaria mostrando o que nao foi alterado
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,  WIDTH  ,  HEIGHT);

        for (Entity e : entities) {
            e.render(g);
        }

        g.dispose();
        g = bs.getDrawGraphics();
        //bota essa tela de fundo no buffer(escalando para a tela)
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE,null);

        bs.show();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
                e.getKeyCode() == KeyEvent.VK_D) {
            player.setRight(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_A) {
            player.setLeft(true);
        }

//        if (e.getKeyCode() == KeyEvent.VK_UP ||
//                e.getKeyCode() == KeyEvent.VK_W) {
//            player.setUp(true);
//        }
//        if (e.getKeyCode() == KeyEvent.VK_DOWN ||
//                e.getKeyCode() == KeyEvent.VK_S) {
//            player.setDown(true);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
                e.getKeyCode() == KeyEvent.VK_D) {
            player.setRight(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_A) {
            player.setLeft(false);
        }

//        if (e.getKeyCode() == KeyEvent.VK_UP ||
//                e.getKeyCode() == KeyEvent.VK_W) {
//            player.setUp(false);
//        }
//        if (e.getKeyCode() == KeyEvent.VK_DOWN ||
//                e.getKeyCode() == KeyEvent.VK_S) {
//            player.setDown(false);
//        }
    }
}
