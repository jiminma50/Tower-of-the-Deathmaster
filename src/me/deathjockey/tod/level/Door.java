package me.deathjockey.tod.level;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.Screen;
import me.deathjockey.tod.screen.UI;
import me.deathjockey.tod.sound.Sound;

public class Door extends Entity {

	int type;
	int cf = 0;
	
	private String[] require;
	private Level level;
	
	boolean opened = false;
	long openTime;
	short openint = 50;
	
	public Door(Level level, int type) {
		super("", "", 1, 0, 0, 0, 0);
		this.type = type;
		hostile = false;
		if(level != null) this.level = level;
		frames = new Bitmap[] { 
			Art.sprites[4 + type][0],
			Art.sprites[4 + type][1],
			Art.sprites[4 + type][2],
			Art.sprites[4 + type][3],
		};
	}
	
	public void setRequire(String req) {
		require = req.split("-");
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
		if(opened) return;
		switch(type) {
		case 0:
			if(player.get("key.yellow") > 0)  {
				player.put("key.yellow", player.get("key.yellow") - 1);
				UI.verbose("Opened Yellow Door! -1 Yellow Key!");
				open();
			} else UI.verbose("This door requires a yellow key!");
			break;
		case 1:
			if(player.get("key.blue") > 0)  {
				player.put("key.blue", player.get("key.blue") - 1);
				UI.verbose("Opened Blue Door! -1 Blue Key!");
				open();
			}else UI.verbose("This door requires a blue key!");
			break;
		case 2:
			if(player.get("key.red") > 0)  {
				player.put("key.red", player.get("key.red") - 1);
				UI.verbose("Opened Red Door! -1 Red Key!");
				open();
			}else UI.verbose("This door requires a red key!");
			break;
		case 3:
			if(player.get("key.green") > 0) {
				player.put("key.green", player.get("key.green") - 1);
				UI.verbose("Opened Mechanical Door! -1 Green Key!");
				open();
			}else UI.verbose("This door requires a green key!");
			break;
		case 4:
			UI.verbose("Opened Cell Bars!");
			open();
			break;
		case 5:
			UI.verbose("Found hidden passage!");
			open();
			break;
		case 6:
			UI.verbose("Found hidden passage!");
			open();
			break;
		case 7:
			UI.verbose("Found hidden passage!");
			open();
			break;
		case 8:
			if(level == null) return;
			int f = 0;
			for(int i = 0; i < require.length; i++) {
				int rx = Integer.parseInt(require[i].split(",")[0]);
				int ry = Integer.parseInt(require[i].split(",")[1]);
				if(level.getEntityAt(-1, rx, ry) == null) {
					f++;
				}
			}
			if(f == require.length) {
				UI.verbose("The mechanical door opened!");
				open();
			} else {
				UI.verbose("Mechanical door is locked!");
			}
			
			break;
		case 9:
			if(player.get("item.tinctureofice") != 0) {
				UI.verbose("The lava magically disappears!");
				open();
			}
			break;
		case 10:
			if(player.get("item.rustykey") != 0) {
				UI.verbose("Magically locked wall opens!");
				open();
			} else {
				UI.verbose("Magic wall requires rusty key!");
			}
			break;
		case 11:
			if(player.get("item.soulkey") != 0) {
				UI.verbose("Magically locked wall opens!");
				open();
			} else {
				UI.verbose("Magic wall requires soul key!");
			}
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
