package me.deathjockey.tod;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import me.deathjockey.tod.dy.DynamicsLoader;
import me.deathjockey.tod.level.Level;
import me.deathjockey.tod.level.Player;
import me.deathjockey.tod.level.Tile;
import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Font;
import me.deathjockey.tod.screen.Screen;
import me.deathjockey.tod.screen.UI;
import me.deathjockey.tod.sound.AudioPlayer;

public class TowerComponent extends Canvas implements Runnable, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = 480;
	private boolean running = false;
	
	public static final String NAME = "Tower of the Deathmaster";
	
	private Screen screen;
	private AudioPlayer audioPlayer;
	private InputHandler input;
	
	private int floor = 1;
	
	public TowerComponent() {
		
	}
	
	long levelTrans;
	boolean hasTrans = false;
	short transint = 1500, dir = 0;
	
	public void upFloor(int tx, int ty) {
		floor++;
		Player p = level.player;
		level.player = null;
		level = Level.levels.get(floor);
		p.level = level;
		p.setPos(tx, ty);
		level.addEntity(p);
		audioPlayer.startBackgroundMusic(floor);
		hasTrans = true;
		levelTrans = System.currentTimeMillis();
		dir = 0;
	}
	
	public void downFloor(int tx, int ty) {
		floor --;
		Player p = level.player;
		level.player = null;
		level = Level.levels.get(floor);
		p.level = level;
		p.setPos(tx, ty);
		level.addEntity(p);
		audioPlayer.startBackgroundMusic(floor);
		hasTrans = true;
		levelTrans = System.currentTimeMillis();
		dir = 1;
	}
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void stop() {
		running = false;
	}
	
	@Override
	public void run() {
		init();
		int fps = 0, tick = 0;
		double fpsTimer = System.currentTimeMillis();
		double nsPerTick = 1000000000.0 / 60;
		double then = System.nanoTime();
		double unp = 0;
		while(running) {
			double now = System.nanoTime();
			unp += (now - then) / nsPerTick;
			then = now;
			while(unp >= 1) {
				tick++;
				tick();
				--unp;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fps++;
			render();
			if(System.currentTimeMillis() - fpsTimer > 1000) {
				System.out.printf("%d fps, %d tick%n", fps, tick);
				fps = 0; tick = 0;
				fpsTimer += 1000;
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		UI.render(screen);
		level.render(screen);
		
		if(hasTrans) {
			if(System.currentTimeMillis() - levelTrans > transint) {
				hasTrans = false;
				levelTrans += transint;
			} else {
				
				screen.render(Art.black, 0, 0);
				if(System.currentTimeMillis() - levelTrans < transint / 2) {
					switch(dir) {
					case 0:
						if(floor >= 0 && floor <= 20) Font.draw(screen, "Floor " + (floor - 1), WIDTH / 2 - Font.getStringWidth("Floor " + floor) / 2, HEIGHT / 4 * 3 - 7);
						if(floor < 0) Font.draw(screen, "Floor " + (floor - 1), WIDTH / 2 - Font.getStringWidth("Floor " + floor) / 2, HEIGHT / 4 * 3 - 7);
						break;
					case 1:
						if(floor >= 0 && floor <= 20) Font.draw(screen, "Floor " + (floor + 1), WIDTH / 2 - Font.getStringWidth("Floor " + floor) / 2, HEIGHT / 4 * 3 - 7);
						if(floor < 0 ) Font.draw(screen, "Floor " + (floor + 1), WIDTH / 2 - Font.getStringWidth("Floor " + floor) / 2, HEIGHT / 4 * 3 - 7);
						break;
					}
				} else {
					Font.draw(screen, "Floor " + floor, WIDTH / 2 - Font.getStringWidth("Floor " + floor) / 2, HEIGHT / 4 * 3 - 7);
				}
				
			}
		}
		
		Font.draw(screen, hx + "," + hy, 10, 10);
		Font.draw(screen, "X", hx * Tile.SIZE + Level.X_OFFSET + (Tile.SIZE / 2 - 7) , Level.Y_OFFSET + hy * Tile.SIZE + (Tile.SIZE / 2 - 7));
		
		UI.extraOverlay(screen);
		
		g.drawImage(screen.image, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
	}

	private void tick() {
		level.tick();
		UI.tick(floor, input);
	}
	
	Level level;

	private void init() {
		addMouseListener(this);
		addMouseMotionListener(this);
		
		screen  = new Screen(WIDTH, HEIGHT);
		input = new InputHandler(this);
		DynamicsLoader.init(this, input);
		level = Level.levels.get(floor);
		audioPlayer = new AudioPlayer();
		audioPlayer.startBackgroundMusic(floor);
		UI.track(level.player);
	}

	public static void main(String[] args) {
		TowerComponent game = new TowerComponent();
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setSize(new Dimension(WIDTH, HEIGHT));
		
		JFrame frame = new JFrame(NAME);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		game.start();
	}
	
	int hx = 0, hy = 0;

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(mx > Level.X_OFFSET && my > Level.Y_OFFSET && mx < Level.X_OFFSET + 12 * Tile.SIZE && my < Level.Y_OFFSET + 13 * Tile.SIZE) {
			hx = (mx - Level.X_OFFSET) / Tile.SIZE;
			hy = (my - Level.Y_OFFSET) / Tile.SIZE;
		}
	}
}
