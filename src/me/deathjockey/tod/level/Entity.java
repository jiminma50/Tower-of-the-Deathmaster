package me.deathjockey.tod.level;

import java.util.HashMap;
import java.util.Map;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.Screen;
import me.deathjockey.tod.screen.UI;
import me.deathjockey.tod.sound.Sound;

public class Entity implements Cloneable {
	
	public static Map<String, Entity> entities = new HashMap<String, Entity>();
	
	public int x, y, w = 32, h = 32;
	public int hp, attack, defense, exp, gold;
	public String name, frame;
	public Bitmap[] frames;
	public boolean removed = false;
	public boolean hostile = true;
	
	private int fi = 250, fr = 0;
	private long ft = System.currentTimeMillis();
	
	public Entity(String name, String frame, int hp, int atk, int def, int exp, int gold) {
		this.name = name;
		this.hp = hp;
		attack = atk;
		defense = def;
		this.frame = frame;
		this.exp = exp;
		this.gold = gold;
		if(frame.equals("")) return;
		int[] frames = new int[2];
		frames[0] = Integer.parseInt(frame.split(",")[0]);
		frames[1] = Integer.parseInt(frame.split(",")[1]);
		this.frames = new Bitmap[2];
		this.frames[0] = Art.sprites[frames[0]][frames[1]];
		this.frames[1] = Art.sprites[frames[0] + 1][frames[1]];
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(Screen screen) {
		screen.render(frames[fr], x * Tile.SIZE + Level.X_OFFSET, y * Tile.SIZE + Level.Y_OFFSET);
	}
	
	public void remove() {
		removed = true;
		if(hostile) {
			player.put("stat.gold", player.get("stat.gold") + gold);
			player.put("stat.exp", player.get("stat.exp") + exp);
			Sound.die.play();
			UI.verbose(name + " was slain!Got " + gold + " GLD and " + exp + " EXP!");
		}
	}
	
	public void tick() {
		if(System.currentTimeMillis() - ft > 2 * fi) ft = System.currentTimeMillis();
		if(System.currentTimeMillis() - ft > fi && frames != null)  {
			if(fr >= frames.length - 1) fr = 0;
			else fr++;
			ft += fi;
		}
	}
	
	public static Entity newInstance(String key) {
		try {
			return (Entity) entities.get(key).clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Player player;

	public void interact(Player player) {
		if(hostile) {
			this.player = player;
			UI.combat(player, this);
		}
	}

	public void render(Screen screen, int i, int j) {
		screen.render(frames[fr], i, j);
	}
	
}
