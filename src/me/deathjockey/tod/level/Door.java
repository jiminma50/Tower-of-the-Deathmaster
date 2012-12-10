package me.deathjockey.tod.level;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.Screen;
import me.deathjockey.tod.sound.Sound;

public class Door extends Entity {

	int type;
	int cf = 0;
	
	boolean opened = false;
	long openTime;
	short openint = 50;
	
	public Door(int type) {
		super("", "", 1, 0, 0, 0, 0);
		this.type = type;
		hostile = false;
		frames = new Bitmap[] { 
			Art.sprites[4 + type][0],
			Art.sprites[4 + type][1],
			Art.sprites[4 + type][2],
			Art.sprites[4 + type][3],
		};
	}
	
	public void render(Screen screen) {
		screen.render(frames[cf], x * Tile.SIZE + Level.X_OFFSET, y * Tile.SIZE + Level.Y_OFFSET);
	}
	
	public void tick() {
		if(opened) {
			if(System.currentTimeMillis() - openTime > openint) {
				if(cf < frames.length - 1) {
					cf++;
				} else {
					remove();
				}
				openTime += openint;
			}
		}
	}
	
	public void interact(Player player) {
		switch(type) {
		case 0:
			if(player.get("key.yellow") > 0)  {
				player.put("key.yellow", player.get("key.yellow") - 1);
				open();
			}
			break;
		case 1:
			if(player.get("key.blue") > 0)  {
				player.put("key.blue", player.get("key.blue") - 1);
				open();
			}
			break;
		case 2:
			if(player.get("key.red") > 0)  {
				player.put("key.red", player.get("key.red") - 1);
				open();
			}
			break;
		case 3:
			if(player.get("key.green") > 0) {
				player.put("key.green", player.get("key.green") - 1);
				open();
				
			}
			break;
		case 4:
			open();
			break;
		}
	}

	private void open() {
		opened = true;
		openTime = System.currentTimeMillis();
		if(type != 4) Sound.door.play();
		else Sound.cell.play();
	}

}
