package me.deathjockey.tod.level;

import me.deathjockey.tod.InputHandler;
import me.deathjockey.tod.TowerComponent;
import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.Screen;

public class Player extends Entity {

	private static final int DIRECTION_UP = 0, DIRECTION_DOWN = 1, DIRECTION_LEFT = 2, DIRECTION_RIGHT = 3;
	
	private int dir = DIRECTION_UP, frame = 0;
	private long lastMove = System.currentTimeMillis(), frameTick = System.currentTimeMillis();
	private short moveint = 100;
	
	private static Bitmap[][] frames = {
		{ Art.sprites[14][15], Art.sprites[15][15] },
		{ Art.sprites[14][12], Art.sprites[15][12] },
		{ Art.sprites[14][13], Art.sprites[15][13] },
		{ Art.sprites[14][14], Art.sprites[15][14] }
	};
	
	private InputHandler input;
	private TowerComponent game;
	public Level level;
	public int lv = 1;

	public int yellowKey = 0, blueKey = 0, redKey = 0;
	
	public Player(InputHandler input, TowerComponent game, Level level) {
		super("Player", "", 1000, 10, 10, 0, 0);
		this.game = game;
		this.input = input;
		this.level = level;
		hostile = false;
	}
	
	public void tick() {
		if(input.up.down) {
			this.dir = DIRECTION_UP;
			move(DIRECTION_UP, 1);
		} else if(input.down.down) {
			this.dir = DIRECTION_DOWN;
			move(DIRECTION_DOWN, 1);
		} else if(input.left.down) {
			this.dir = DIRECTION_LEFT;
			move(DIRECTION_LEFT, 1);
		} else if(input.right.down) {
			this.dir = DIRECTION_RIGHT;
			move(DIRECTION_RIGHT, 1);
		} else {
			if(System.currentTimeMillis() - lastMove > moveint) {
				lastMove += moveint;
			}
		}
		
		if(frame == 1 && System.currentTimeMillis() - frameTick > 200) {
			frame = 0;
			frameTick = System.currentTimeMillis();
		}
	}
	
	private void move(int dir, int amount) {
		if(System.currentTimeMillis() - lastMove < moveint) return;
		int intent = -1;
		if(dir == DIRECTION_LEFT) {
			intent = DIRECTION_LEFT;
			if(canMove(DIRECTION_LEFT)) {
				x -= amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
			}
		}
		if(dir == DIRECTION_RIGHT) {
			intent = DIRECTION_RIGHT;
			if(canMove(DIRECTION_RIGHT)) {
				x += amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
			}
		}
		if(dir == DIRECTION_UP) {
			intent = DIRECTION_UP;
			if(canMove(DIRECTION_UP)) { 
				y -= amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
			}
		}
		if(dir == DIRECTION_DOWN) {
			intent = DIRECTION_DOWN;
			if(canMove(DIRECTION_DOWN)) {
				y += amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
			}
		}
		
		Entity facing = level.getEntityAt(intent, x, y);
		if(facing != null && facing.hostile) {
			move(invertDir(intent), 1);
		} 
		if(facing instanceof Stairs) {
			((Stairs) facing).use();
		}
		lastMove += moveint;
	}
	
	private int invertDir(int dir) {
		if(dir == DIRECTION_UP) return DIRECTION_DOWN;
		if(dir == DIRECTION_DOWN) return DIRECTION_UP;
		if(dir == DIRECTION_LEFT) return DIRECTION_RIGHT;
		if(dir == DIRECTION_RIGHT) return DIRECTION_LEFT;
		return 0;
	}

	private boolean canMove(int dir) {
		if(dir == DIRECTION_LEFT) {
			Tile t = level.getTileAt(x - 1, y);
			if(t != null && !t.isSolid()) return true;
			else return false;
		}
		if(dir == DIRECTION_RIGHT) {
			Tile t = level.getTileAt(x + 1, y);
			if(t != null && !t.isSolid()) return true;
			else return false;
		}
		if(dir == DIRECTION_UP) {
			Tile t = level.getTileAt(x, y - 1);
			if(t != null && !t.isSolid()) return true;
			else return false;
		}
		if(dir == DIRECTION_DOWN) {
			Tile t = level.getTileAt(x, y + 1);
			if(t != null && !t.isSolid()) return true;
			else return false;
		}
		return false;
	}

	public void render(Screen screen) {
		screen.render(frames[dir][frame], x * Tile.SIZE + Level.X_OFFSET, y * Tile.SIZE + Level.Y_OFFSET);
	}

}
