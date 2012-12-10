package me.deathjockey.tod.level;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.Screen;

public class Tile {

	public static final int SIZE = 32;
	
	public static Tile[] tiles = new Tile[16];
	
	protected boolean solid = false;
	protected String name;
	
	protected Bitmap[] frames;
	protected int cf = 0, ci = 500;
	protected long lt = System.currentTimeMillis();
	
	public byte id;
	
	public Tile(int id, String name, boolean isSolid, String frames) {
		if(tiles[id] != null) {
			throw new RuntimeException("Duplicated Tile ID! " + id);
		} else{
			tiles[id] = this;
			this.id = (byte) id;
			this.name = name;
			String[] framelets = frames.split(">");
			this.frames = new Bitmap[framelets.length];
			for(int i = 0; i < framelets.length; i++) {
				String[] ind = framelets[i].split(",");
				int fx = Integer.parseInt(ind[0]);
				int fy = Integer.parseInt(ind[1]);
				this.frames[i] = Art.sprites[fx][fy];
			}
			solid = isSolid;
			
		}
	}
	
	public void render(Screen screen, int x, int y) {
		screen.render(frames[cf], x, y);
		if(System.currentTimeMillis() - lt > ci) {
			if(cf >= frames.length - 1) cf = 0;
			else cf++;
			lt += ci;
		}
	}
	
	public boolean isSolid() {
		return solid;
	}
}
