package me.deathjockey.tod.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Screen;
import me.deathjockey.tod.screen.UI;

public class Shop extends Entity {

	public int use = 0;
	
	public Map<String, Integer> sale = new HashMap<String, Integer>();
	public List<Integer> price = new ArrayList<Integer>();
	public List<String> sellList = new ArrayList<String>();
	
	public Shop(String use, String sell) {
		super("", "8,5", 1, 0,0,0,0);
		hostile = false;
		this.use = (use.equalsIgnoreCase("GLD")) ? 0 : 1;
		String[] sales = sell.split(",");
		for(int i = 0; i < sales.length; i++) {
			String[] selling = sales[i].split(":");
			
			String[] kv = selling[1].split("-");
			int price = Integer.parseInt(kv[1]);
			sale.put(selling[0], Integer.parseInt(kv[0]));
			sellList.add(selling[0]);
			this.price.add(price);
		}
	}
	
	public void render(Screen screen) {
		super.render(screen);
		screen.render(Art.sprites[7][5], (x - 1) * Tile.SIZE + Level.X_OFFSET, y * Tile.SIZE + Level.Y_OFFSET);
		screen.render(Art.sprites[10][5], (x + 1) * Tile.SIZE + Level.X_OFFSET, y * Tile.SIZE + Level.Y_OFFSET);
	}
	
	public void interact(Player player) {
		UI.shop(this, player);
	}
}
