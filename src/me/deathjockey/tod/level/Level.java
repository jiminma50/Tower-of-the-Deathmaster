package me.deathjockey.tod.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.deathjockey.tod.screen.Screen;

public class Level {
	
	public static Map<Integer, Level> levels = new HashMap<Integer, Level>();

	public int floor;
	
	public int[][] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();
	public Player player;
	public static final int Y_OFFSET = 1 * Tile.SIZE, X_OFFSET = 7 * Tile.SIZE;
	
	public Level() {
		tiles = new int[12][13];
	}
	
	public void render(Screen screen) {
		for(int i = 0; i < tiles.length; i++) 
			for(int j = 0; j < tiles[i].length; j++) {
				if(Tile.tiles[tiles[i][j]] == null) continue;
				Tile.tiles[tiles[i][j]].render(screen, X_OFFSET + i * Tile.SIZE, Y_OFFSET + j * Tile.SIZE);
			}
		
		for(Entity e : entities) {
			e.render(screen);
			
		}
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
			if(entities.get(i).removed) {
				entities.remove(i);
			}
		}
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
		if(entity instanceof Player) {
			this.player = (Player) entity;
//			entities.add(entity);
		}
//		if((entity instanceof Stairs)) {
//			entities.add(entity);
//		}
	}

	public Tile getTileAt(int i, int j) {
		if(i < 0 || i >= tiles.length || j < 0 || j > tiles.length) return null;
		return Tile.tiles[tiles[i][j]];
	}

	public Entity getEntityAt(int dir, int x, int y) {
		for(Entity e : entities) {
			if(e.x == x && e.y == y && ((e instanceof NPC) || (e instanceof Door))) return e;
		}
		for(Entity e : entities) {
			if(e.x == x && e.y == y && !(e instanceof Player)) return e;
		}
		return null;
	}

	public List<Entity> getEntities() {
		return entities;
	}

}
