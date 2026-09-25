package com.josuesch;

import com.josuesch.assets.Placeble;
import com.josuesch.entities.Ball;
import com.josuesch.entities.Entity;
import com.josuesch.entities.Player;
import com.josuesch.graphics.Spritesheet;
import com.josuesch.graphics.UI;
import com.josuesch.world.World;
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
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

    private Thread thread;
    private boolean isRunning = false;
    private static JFrame frame;

    public static final int WIDTH = 272;
    public static final int HEIGHT = 336;
    public static final int SCALE = 2;
    public static final Spritesheet SPRITESHEET = new Spritesheet("/spritesheet.png");
    public static final Random RANDOM = new Random();

    private BufferedImage image;

    public static Player player;

    private List<Entity> entities;
    private List<Ball> balls;
    private World world;
    private UI ui;

    private boolean started = false;
    private boolean gameOver = false;

    private final List<Placeble> collidablesBuffer = new ArrayList<>();

    private final List<Entity> entitiesToRemove = new ArrayList<>();

    public List<Placeble> getCollidables() {
        collidablesBuffer.clear();
        collidablesBuffer.addAll(World.getSolidTiles());
        collidablesBuffer.addAll(entities);
        return collidablesBuffer;
    }

    public Game() {
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Cria a tela de fundo onde vamos desenhar tudo;
        entities = new ArrayList<Entity>();
        balls = new ArrayList<Ball>();
        player = new Player(this, 100, 180, 32, 8);
        entities.add(player);
        world = new World(this, "/map.png");

        ui = new UI(this);
    }

    private void initFrame() {
        frame = new JFrame("BrickBreaker");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void restart() {
        entities.clear();
        player = new Player(this, 100, 180, 32, 8);
        entities.add(player);
        world = new World(this, "/map.png");
        gameOver = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunning) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    private void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }

        if (!entitiesToRemove.isEmpty()) {
            entities.removeAll(entitiesToRemove);
            balls.removeAll(entitiesToRemove);
            entitiesToRemove.clear();
        }

        if (started && balls.isEmpty()) gameOver = true;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy(); // pega a que ja tem
        if (bs == null) {
            this.createBufferStrategy(3); // se nao tem cria uma nova
            return;
        }
        // desenha na tela de fundo
        Graphics g = image.getGraphics();

        // preenche a ultima tela, se nao ficaria mostrando o que nao foi alterado
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);

        for (Entity e : entities) {
            e.render(g);
        }

        ui.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        // bota essa tela de fundo no buffer(escalando para a tela)
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        bs.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (!started || gameOver) {
            // TODO refector 2 internal ifs
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (gameOver) restart();
                var ball =
                        new Ball(
                                this,
                                player.getX() + player.getWidth() / 2.0 - 2,
                                player.getY() - 5,
                                5,
                                5,
                                Math.toRadians(270));
                addBall(ball);
                if (!gameOver) started = true;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                player.setRight(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                player.setLeft(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.setRight(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.setLeft(false);
        }
    }

    public void addBall(Ball ball) {
        entities.add(ball);
        balls.add(ball);
    }

    public void removeBall(Ball ball) {
        entities.remove(ball);
        balls.remove(ball);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasNotStarted() {
        return !started;
    }

    public void queueRemoval(Entity entity) {
        entitiesToRemove.add(entity);
    }
}
