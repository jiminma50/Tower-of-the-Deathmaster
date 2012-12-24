package me.deathjockey.tod.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.UI;
import me.deathjockey.tod.sound.Sound;

public class Item extends Entity {

	public static Map<String, Item> items = new HashMap<String, Item>();
	
	private String description = "";
	private Map<String, Integer> give = new HashMap<String, Integer>();
	private List<String> giveList = new ArrayList<String>();
	
	public Item(String name, String frame, String des, String give) {
		super(name, "", 1, 0, 1000000, 0, 0);
		this.name = name;
		description = des;
		hostile = false;
		if(frame.equals("")) return;
		String[] fxy = frame.split(",");
		int[] ixy = new int[2];
		ixy[0] = Integer.parseInt(fxy[0]);
		ixy[1] = Integer.parseInt(fxy[1]);
		frames = new Bitmap[] { Art.sprites[ixy[0]][ixy[1]] };
		
		String[] chains = give.split(",");
		for(int i = 0; i < chains.length; i++) {
			String[] kv = chains[i].split(":");
			this.give.put(kv[0], Integer.parseInt(kv[1]));
			this.giveList.add(kv[0]);
		}
	}
	
	public static Item newInstance(String key) {
		try {
			return (Item) items.get(key).clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void interact(Player player) {
		for(int i = 0; i < giveList.size(); i++) {
			player.put(giveList.get(i), player.get(giveList.get(i)) + give.get(giveList.get(i)));
			if(giveList.get(i).equalsIgnoreCase("stat.level")) {
				for(int j = 0; j < give.get(giveList.get(i)); j++) {
					player.levelUp();
				}
			}
		}
		UI.verbose("Picked up " + name + "!" + description);
		Sound.pickup.play();
		remove();
	}
}
