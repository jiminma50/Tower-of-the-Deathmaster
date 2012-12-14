package me.deathjockey.tod.level;

import me.deathjockey.tod.TowerComponent;
import me.deathjockey.tod.screen.Art;
import me.deathjockey.tod.screen.Bitmap;
import me.deathjockey.tod.sound.Sound;

public class Stairs extends Entity {

	int dir;
	TowerComponent game;
	int tx, ty;
	
	public Stairs(TowerComponent game, int dir, int tx, int ty) {
		super("", "", 1, 0, 10000000, 0, 0);
		hostile = false;
		this.game = game;
		this.dir = dir;
		this.frames = new Bitmap[] { (dir == 0) ? Art.sprites[4][6] : Art.sprites[4][5] };
		this.tx = tx;
		this.ty = ty;
	}
	
	public void interact(Player player) {
		switch(dir) {
		case 0:
			game.upFloor(tx, ty);
			break;
		case 1:
			game.downFloor(tx, ty);
			break;
		}
		Sound.stairs.play();
		player.lastMove = System.currentTimeMillis() + 1500;
	}	
}
