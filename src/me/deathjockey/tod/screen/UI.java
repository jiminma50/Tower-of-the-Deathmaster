package me.deathjockey.tod.screen;

import me.deathjockey.tod.TowerComponent;
import me.deathjockey.tod.level.Player;
import me.deathjockey.tod.level.Tile;

public class UI {

	public static void render(Screen screen) {
		for(int i = 0; i < TowerComponent.WIDTH / Tile.SIZE; i++) {
			for(int j = 0; j < TowerComponent.HEIGHT / Tile.SIZE; j++) {
				screen.render(Art.sprites[0][0], i * Tile.SIZE, j * Tile.SIZE);
			}
		}
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 13; j++) {
				screen.render(Art.sprites[3][0], (i + 7) * Tile.SIZE, (j + 1) * Tile.SIZE);
			}
		}
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 8; j++) {
				screen.render(Art.sprites[3][0], (i + 1) * Tile.SIZE, (j + 1) * Tile.SIZE);
			}
		}
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 4; j++) {
				screen.render(Art.sprites[3][0], (i + 1) * Tile.SIZE, (j + 10) * Tile.SIZE);
			}
		}
		screen.render(Art.sprites[4][4], 40, 320);
		screen.render(Art.sprites[5][4], 40, 350);
		screen.render(Art.sprites[6][4], 40, 380);
		screen.render(Art.sprites[7][4], 40, 410);
		screen.render(Art.sprites[14][12], 45, 45);
		
		Font.draw(screen, "Lv:" + player.get("stat.level"), 80, 60);
		Font.draw(screen, "HP :" + player.get("stat.hp"), 45, 100);
		Font.draw(screen, "Atk:" + player.get("stat.attack"), 45, 130);
		Font.draw(screen, "Def:" + player.get("stat.defense"), 45, 160);
		Font.draw(screen, "Gld:" + player.get("stat.gold"), 45, 190);
		Font.draw(screen, "Exp:" + player.get("stat.exp"), 45, 220);
		
		Font.draw(screen, "Flr:" + floor, 45, 260);
		
		Font.draw(screen, "X "+ player.get("key.yellow"), 85, 330);
		Font.draw(screen, "X " + player.get("key.blue"), 85, 360);
		Font.draw(screen, "X " + player.get("key.red"), 85, 390);
		Font.draw(screen, "X " + player.get("key.green"), 85, 420);
	}
	
	static Player player;

	public static void track(Player player) {
		UI.player = player;
	}
	
	static int floor = 0;
	
	public static void tick(int floor) {
		UI.floor = floor;
	}
}
