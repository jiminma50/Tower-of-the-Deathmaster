package me.deathjockey.tod.level;

import java.util.HashMap;
import java.util.Map;

import me.deathjockey.tod.InputHandler;
import me.deathjockey.tod.TowerComponent;
import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.Screen;
import me.deathjockey.tod.screen.UI;
import me.deathjockey.tod.sound.Sound;

public class Player extends Entity {

	private static final int DIRECTION_UP = 0, DIRECTION_DOWN = 1, DIRECTION_LEFT = 2, DIRECTION_RIGHT = 3;
	private Map<String, Integer> status = new HashMap<String, Integer>();
	int dir = DIRECTION_UP;
	private int frame = 0;
	public long lastMove = System.currentTimeMillis(), frameTick = System.currentTimeMillis();
	private short moveint = 100;
	
	private static Bitmap[][] frames = {
		{ Art.sprites[14][15], Art.sprites[15][15] },
		{ Art.sprites[14][12], Art.sprites[15][12] },
		{ Art.sprites[14][13], Art.sprites[15][13] },
		{ Art.sprites[14][14], Art.sprites[15][14] }
	};
	
	public boolean canmove = true;
	
	private InputHandler input;
	private TowerComponent game;
	public Level level;
	
	public Player(InputHandler input, TowerComponent game, Level level) {
		super("Player", "", 1, 10, 10, 0, 0);
		put("stat.hp", 15897);
		put("stat.level", 27);
		put("stat.attack", 680);
		put("stat.defense", 561);
		put("stat.exp", 67);
		put("stat.gold", 23);
		put("key.yellow", 5);
		put("key.blue", 6);
		put("key.red", 1);
		put("key.green", 0);
		put("item.rod", 1);
		put("item.rustykey", 1);
		put("item.soulkey", 0);
		put("item.cross", 0);
		//pots
		put("item.tinctureofice", 1);
		
		this.game = game;
		this.input = input;
		this.level = level;
		hostile = false;
	}
	
	public void put(String key, int value) {
		status.put(key, value);
		if(status.get(key) < 0) {
			status.put(key, 0);
		}
		
	}
	
	public void levelUp() {
		status.put("stat.hp", status.get("stat.hp") + 750);
		status.put("stat.attack", status.get("stat.attack") + 7);
		status.put("stat.defense", status.get("stat.defense") + 7);
	}

	public int get(String key) {
		return status.get(key);
	}
	
	public void tick() {
		if(!canmove) lastMove = System.currentTimeMillis();
		if(input.up.down) {
			if(!canmove) return;
			this.dir = DIRECTION_UP;
			move(DIRECTION_UP, 1);
		} else if(input.down.down) {
			if(!canmove) return;
			this.dir = DIRECTION_DOWN;
			move(DIRECTION_DOWN, 1);
		} else if(input.left.down) {
			if(!canmove) return;
			this.dir = DIRECTION_LEFT;
			move(DIRECTION_LEFT, 1);
		} else if(input.right.down) {
			if(!canmove) return;
			this.dir = DIRECTION_RIGHT;
			move(DIRECTION_RIGHT, 1);
		} else {			
			if(System.currentTimeMillis() - lastMove > moveint) {
				lastMove += moveint;
			}
		}
		
		if(input.use_rod.down && !UI.usingRod) {
			if(this.get("item.rod") != 0) {
				UI.useRod(this, level);
			}
		}
		
		if(frame == 1 && System.currentTimeMillis() - frameTick > 200) {
			frame = 0;
			frameTick = System.currentTimeMillis();
		}
	}
	
	public void move(int dir, int amount) {
		if(!canmove) return;
		if(System.currentTimeMillis() - lastMove < moveint) return;
		int intent = -1;
		if(dir == DIRECTION_LEFT) {
			intent = DIRECTION_LEFT;
			if(canMove(DIRECTION_LEFT)) {
				x -= amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
				Sound.walk.play();
			}
		}
		if(dir == DIRECTION_RIGHT) {
			intent = DIRECTION_RIGHT;
			if(canMove(DIRECTION_RIGHT)) {
				x += amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
				Sound.walk.play();
			}
		}
		if(dir == DIRECTION_UP) {
			intent = DIRECTION_UP;
			if(canMove(DIRECTION_UP)) { 
				y -= amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
				Sound.walk.play();
			}
		}
		if(dir == DIRECTION_DOWN) {
			intent = DIRECTION_DOWN;
			if(canMove(DIRECTION_DOWN)) {
				y += amount;
				frame = 1;
				frameTick = System.currentTimeMillis();
				Sound.walk.play();
			}
		}
		
		Entity facing = level.getEntityAt(intent, x, y);
		if(facing != null && facing.hostile) {
			move(invertDir(intent), 1);
			facing.interact(this);
		} 
		if(facing instanceof Stairs) {
			((Stairs) facing).interact(this);
		}
		if(facing instanceof Door || facing instanceof Item || facing instanceof Shop || facing instanceof NPC) {
			move(invertDir(intent), 1);
			(facing).interact(this);
		}
		lastMove += moveint;
	}
	
	public int invertDir(int dir) {
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
