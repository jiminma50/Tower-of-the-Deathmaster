package me.deathjockey.tod.screen;

import java.util.ArrayList;
import java.util.List;

import me.deathjockey.tod.InputHandler;
import me.deathjockey.tod.TowerComponent;
import me.deathjockey.tod.level.Combat;
import me.deathjockey.tod.level.Entity;
import me.deathjockey.tod.level.Level;
import me.deathjockey.tod.level.NPC;
import me.deathjockey.tod.level.Player;
import me.deathjockey.tod.level.Shop;
import me.deathjockey.tod.level.Tile;
import me.deathjockey.tod.sound.Sound;

public class UI {

	static String verbose = "";
	private static long lastVerbose = System.currentTimeMillis();
	
	public static void verbose(String string) {
		verbose = string;
		lastVerbose = System.currentTimeMillis();
	}
	
	public static void render(Screen screen) {
		if(player == null) return;
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
		if(System.currentTimeMillis() - lastVerbose < 2500 + verbose.split(" ").length * 250) Font.draw(screen, verbose, TowerComponent.WIDTH / 2 - Font.getStringWidth(verbose) / 2, 10);
		
	}
	
	public static void extraOverlay(Screen screen) {
		if(hascombat) {
			for(int i = 0; i < 14; i++) {
				for(int j = 0; j < 8; j++) {
					if(i == 0 || i == 13 || j == 0 || j == 7) screen.render(Art.sprites[0][0], (i + 3) * Tile.SIZE, (j + 3) * Tile.SIZE);
					else screen.render(Art.sprites[3][0], (i + 3) * Tile.SIZE, (j + 3) * Tile.SIZE);
				}
			}
			Font.draw(screen, "Fighting " + combatEntity.name, TowerComponent.WIDTH / 2 - Font.getStringWidth("Fighting " + combatEntity.name) / 2, 105);
			screen.render(Art.sprites[14][12], 450, 140);
			combatEntity.render(screen, 150, 140);
			Font.draw(screen, "HP :" + combatEntity.hp, 140, 200);
			Font.draw(screen, "Atk:" + combatEntity.attack, 140, 240);
			Font.draw(screen, "Def:" + combatEntity.defense, 140, 280);
			screen.render(Art.pk, 240, 140);
			Font.draw(screen, ": HP" , 450, 200);
			Font.draw(screen, ":Atk" , 450, 240);
			Font.draw(screen, ":Def" , 450, 280);
			
			Font.draw(screen, player.get("stat.hp") + "" , 430 - Font.getStringWidth(player.get("stat.hp") + ""), 200);
			Font.draw(screen, player.get("stat.attack") + "" , 430 - Font.getStringWidth(player.get("stat.attack") + ""), 240);
			Font.draw(screen, player.get("stat.defense") + "" , 430 - Font.getStringWidth(player.get("stat.defense") + ""), 280);
			
			if(won) {
				if(System.currentTimeMillis() - lastWin >= 500) {
					won = false;
					hascombat = false;
					player.canmove = true;
				}
			}
			
		}
		if(hasshop) {
			if(hasshop) {
				for(int i = 0; i < 14; i++) {
					for(int j = 0; j < 10; j++) {
						if(i == 0 || i == 13 || j == 0 || j == 9) screen.render(Art.sprites[0][0], (i + 3) * Tile.SIZE, (j + 2) * Tile.SIZE);
						else screen.render(Art.sprites[3][0], (i + 3) * Tile.SIZE, (j + 2) * Tile.SIZE);
					}
				}
				Font.draw(screen, "- God of War -", TowerComponent.WIDTH / 2 - Font.getStringWidth("- God of War -") / 2, 75);
				screen.render(Art.sprites[8][6], 96, 64);
				screen.render(Art.sprites[8][5], 96, 64);
				if(page == 0) {
					Font.draw(screen, "Mortals, I can grant you power", 140, 110);
					Font.draw(screen, "if you give me gold.", 140, 130);
					Font.draw(screen, "Press 2 and 8 to navigate", 140, 190);
					Font.draw(screen, "and use SPACE to select!", 140, 210);
					Font.draw(screen, "press ESC to exit window!", 140, 230);
					Font.draw(screen, "I don't do refunds, sorry!", 140, 250);
					Font.draw(screen, "- Press Enter -", 140, 320);
				} else {
					Font.draw(screen, "How may I help you, mortal?", TowerComponent.WIDTH / 2 - Font.getStringWidth("How may I help you, mortal?") / 2, 110);
					for(int i = 0; i < shop.sellList.size(); i++) {
						Font.draw(screen, shop.sale.get(shop.sellList.get(i)) + " " + shop.sellList.get(i).replace("stat.", "") + " for " + shop.price.get(i) + ((shop.use == 0) ? " GLD" : " EXP"), TowerComponent.WIDTH / 2 - Font.getStringWidth(shop.sale.get(shop.sellList.get(i)) + " " + shop.sellList.get(i).replace("stat.", "") + " for " + shop.price.get(i) + ((shop.use == 0) ? " GLD" : " EXP")) / 2, 200 + i * 50);
					}
					screen.render(Art.sprites[10][4], 145, 190 + selection * 50);
				}
				
			}
		}
		
		if(hasdialog) {
			for(int i = 0; i < 14; i++) {
				for(int j = 0; j < 10; j++) {
					if(i == 0 || i == 13 || j == 0 || j == 9) screen.render(Art.sprites[0][0], (i + 3) * Tile.SIZE, (j + 2) * Tile.SIZE);
					else screen.render(Art.sprites[3][0], (i + 3) * Tile.SIZE, (j + 2) * Tile.SIZE);
				}
			}
			Font.draw(screen, npc.title, TowerComponent.WIDTH / 2 - Font.getStringWidth(npc.title) / 2, 75);
			for(int i = 0; i < npc.text.length; i++) {
				Font.draw(screen, npc.text[i], 145, 110 + i * 20);
			}
			Font.draw(screen, "-Space-", TowerComponent.WIDTH / 2 - Font.getStringWidth("-Space-") / 2, 360);
		}
		
		if(usingRod) {
			for(int i = 0; i < 12; i++) {
				for(int j = 0; j < 13; j++) {
					screen.render(Art.sprites[3][1], i * Tile.SIZE + Level.X_OFFSET, j * Tile.SIZE + Level.Y_OFFSET);
				}
			}
			Font.draw(screen, "Ability Rod - " + cpage + " of " + cpage_max, TowerComponent.WIDTH / 2 - Font.getStringWidth("Ability Rod") / 2, 10);
			Font.draw(screen, "(ESC) to exit", TowerComponent.WIDTH / 2 - Font.getStringWidth("(ESC) to exit") / 2, TowerComponent.HEIGHT - 25);
			int cw = 0;
			for(int i = cpage * 5; (cpage * 5 + 5 >= entities.size() - 1) ? i < entities.size() : i < cpage * 5 + 5; i++) {
//				System.out.println("getting " + i);
				Entity e = entities.get(i);
				e.render(screen, Level.X_OFFSET + 20, Level.Y_OFFSET + 20 + i * 84 - (cpage * 5 * 84));
				Font.draw(screen, e.name, Level.X_OFFSET + 20, Level.Y_OFFSET + 55 + i * 84 - (cpage * 5 * 84));
				Font.draw(screen, "HP:" + e.hp + " DEF:" + e.defense + " DMG:" + ((Combat.calculateDamage(player, e) != -1) ? Combat.calculateDamage(player, e) : "999999"),Level.X_OFFSET + 60, Level.Y_OFFSET + 15 + i * 84 - (cpage * 5 * 84));
				Font.draw(screen, "ATK:" + e.attack + " GLD/EXP:" + e.gold + "/" + e.exp,Level.X_OFFSET + 60, Level.Y_OFFSET + 35 + i * 84 - (cpage * 5 * 84));
				cw++;
				if(cw >= 5) {
					cw = 0;
					break;
				}
			}
		}
	}
	
	static Player player;
	static Entity combatEntity;

	public static void track(Player player) {
		UI.player = player;
	}
	
	static int floor = 0;
	
	public static void tick(int floor, InputHandler input) {
		UI.floor = floor;
		
		if(hascombat && System.currentTimeMillis() - lastHit > hitint) {
			if(won) return;
			switch(turn) {
			case 0:
				int ch = (int) (Math.random() * 15);
				boolean c = (ch == 0) ? true : false;
				int dmg = 0;
				if(c) {
					dmg = player.get("stat.attack") * 2 - combatEntity.defense;
				} else {
					dmg = player.get("stat.attack") - combatEntity.defense;
				}
				if(combatEntity.hp - dmg < 0) {
					dmg = combatEntity.hp;
				}
				combatEntity.hp -= dmg;
				hit = 1;
				lastHit += hitint;
				turn = 1;
				if(c) {
					Sound.criticalHit.play();
				} else {
					Sound.hit.play();
				}
				break;
			case 1:
				int edmg = combatEntity.attack - player.get("stat.defense");
				if(player.get("stat.hp") - edmg < 0) {
					edmg = player.get("stat.hp");
				}
				if(edmg < 0) edmg = 0;
				player.put("stat.hp", player.get("stat.hp") - edmg);
				hit = 0;
				lastHit += hitint;
				turn = 0;
				if(edmg == 0) {
					Sound.parry.play();
				} else {
					Sound.hit.play();
				}
				break;
			}
			if(combatEntity.hp <= 0) {
//				hascombat = false;
				hit = -1;
				turn = 0;
				lastWin = System.currentTimeMillis();
				won = true;
				combatEntity.remove();
			}
		}
		
		if(hasshop) {
			if(page == 0 && input.enter.down) page = 1;
			if(input.cursor_up.down) {
				if(System.currentTimeMillis() - lastkp > kpint) {
					if(selection + 1 < selllen) selection++;
					else selection = 0;
					Sound.select.play();
					lastkp += kpint;
				} else if(System.currentTimeMillis() - lastkp > kpint) lastkp += kpint;
			}
			else if(input.cursor_down.down) {
				if(System.currentTimeMillis() - lastkp > kpint) {
					if(selection > 0) selection--;
					else selection = selllen - 1;
					Sound.select.play();
					lastkp += kpint;
				} else if(System.currentTimeMillis() - lastkp > kpint) lastkp += kpint;
			}
			else if(input.escape.down) {
				hasshop = false;
				page = 0;
				selection = 0;
				player.canmove = true;
			}
			else if(input.confirm.down) {
				if(System.currentTimeMillis() - lastkp > kpint) {
					if(!(page == 1)) return;
					if((shop.use == 0 && player.get("stat.gold") >= shop.price.get(selection)) || (shop.use == 1 && player.get("stat.exp") >= shop.price.get(selection))) {
						player.put(shop.sellList.get(selection), player.get(shop.sellList.get(selection)) + shop.sale.get(shop.sellList.get(selection)));
						if(shop.sellList.get(selection).equals("stat.level")) player.levelUp();
						if(shop.use == 0) player.put("stat.gold", player.get("stat.gold") - shop.price.get(selection));
						else player.put("stat.exp", player.get("stat.exp") - shop.price.get(selection));
						Sound.buy.play();
					} else {
						verbose("Cannot purchase! This costs " + shop.price.get(selection) + "!");
					}
					lastkp += kpint;
				} else if(System.currentTimeMillis() - lastkp > kpint) lastkp += kpint;
			}
			else {
				if(System.currentTimeMillis() - lastkp > kpint) lastkp += kpint;
			}
		} else {
			if(System.currentTimeMillis() - lastkp > kpint) lastkp += kpint;
		}
		if(hasdialog) {
			if(input.confirm.down) {
				hasdialog = false;
				player.canmove = true;
				if(npc.disappear) {
					npc.remove();
				}
			}
		}
		
		if(usingRod) {
			if(input.escape.down) {
				usingRod = false;
				player.canmove = true;
			}
			if(input.left.down) {
				if(System.currentTimeMillis() - pagetoggle > pageint) {
					if(cpage > 0) cpage--;
					pagetoggle += pageint;
				}
			} else if(input.right.down) {
				if(System.currentTimeMillis() - pagetoggle > pageint) {
					if(cpage < cpage_max) {
						cpage++;
					}
					pagetoggle += pageint;
				}
			} else {
				if(System.currentTimeMillis() - pagetoggle > pageint) {
					pagetoggle += pageint;
				}
			}
		} else {
			if(System.currentTimeMillis() - pagetoggle > pageint) {
				pagetoggle += pageint;
			}
		}
	}
	
	static int pageint = 500;
	static long pagetoggle = System.currentTimeMillis();
	
	
	static short hit = -1;
	static boolean hascombat = false, won = false;
	static long lastHit, lastWin;
	static short hitint = 250, turn = 0;

	public static void combat(Player player, Entity monster) {
		if(Combat.calculateDamage(player, monster) < 0) {
			verbose(monster.name + " is too strong!");
			return;
		}
		hascombat = true;
		turn = 0;
		lastHit = System.currentTimeMillis();
		combatEntity = monster;
		player.canmove = false;
	}
	
	static boolean hasshop = false;
	static int page = 0, selllen = 0;
	static int selection = 0;
	static Shop shop;
	
	static long lastkp = System.currentTimeMillis();
	static short kpint = 200;

	public static void shop(Shop shop, Player p) {
		hasshop = true;
		UI.shop = shop;
		selllen = shop.sellList.size(); 
		lastkp = System.currentTimeMillis();
		p.canmove = false;
	}
	
	static boolean hasdialog = false;
	static NPC npc;

	public static void npc(NPC npc, Player p) {
		UI.npc = npc;
		UI.player = p;
		hasdialog = true;
		p.canmove = false;
	}
	
	static Level level;
	public static boolean usingRod = false;
	
	static int cpage = 0, cpage_max = 0;
	
	static List<Entity> entities;
	
	public static void useRod(Player player, Level level) {
		UI.player = player;
		UI.level = level;
		usingRod = true;
		player.canmove = false;
		cpage = 0;
		List<Entity> allents = level.getEntities();
		List<Entity> hostile = new ArrayList<Entity>();
		List<String> checked = new ArrayList<String>();
		entities = new ArrayList<Entity>();
		for(int i = 0; i < allents.size(); i++) {
			if(allents.get(i).hostile) hostile.add(allents.get(i));
		}
		outer:
		for(int i = 0; i < hostile.size(); i++) {
			if(i == 0) {
				entities.add(hostile.get(i));
				checked.add(hostile.get(i).name);
				continue;
			}
			for(int j = 0; j < checked.size(); j++) {
				if(hostile.get(i).name.equals(checked.get(j))) {
					
					continue outer;
				}
			}
			entities.add(hostile.get(i));
			checked.add(hostile.get(i).name);
		}
		cpage_max = entities.size() / 5;
	}
}
