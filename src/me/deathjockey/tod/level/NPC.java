package me.deathjockey.tod.level;

import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.screen.UI;

public class NPC extends Entity {

	public String title;
	public String[] text;
	public boolean disappear;
	
	public NPC(String title, String text, boolean disappear, int type) {
		super("", "", 1, 0, 1000000, 0, 0);
		this.title = title;
		this.text = text.split("#");
		hostile = false;
		this.disappear = disappear;
		switch(type) {
		case 0:
			frames = new Bitmap[] { Art.sprites[0][12], Art.sprites[1][12] };
			break;
		case 1:
			frames = new Bitmap[] { Art.sprites[0][13], Art.sprites[1][13] };
			break;
		case 2:
			frames = new Bitmap[] { Art.sprites[0][14], Art.sprites[1][14] };
			break;
		case 3:
			frames = new Bitmap[] { Art.sprites[0][15], Art.sprites[1][15] };
			break;
		case 4:
			frames = new Bitmap[] { Art.sprites[2][12], Art.sprites[3][12] };
			break;
		}
	}
	
	public void interact(Player player) {
		UI.npc(this, player);
		player.move(player.invertDir(player.dir),1);
	}
}
