package me.deathjockey.tod.level;

public class Combat {
	
	public static int calculateDamage(Player player, Entity entity) {
		if(!entity.hostile) return 0;
		int php = player.get("stat.hp");
		int pdef = player.get("stat.defense");
		int patk = player.get("stat.attack");
		
		int ehp = entity.hp;
		int eatk = entity.attack;
		int edef = entity.defense;
		
		int accumulate = 0;
		
		int turn = 0;
		boolean canend = false;
		while(!canend) {
			switch(turn) {
			case 0:
				int dmg = patk - edef;
				if(dmg <= 0) return -1;
				else {
					ehp -= dmg;
					turn = 1;
					break;
				}
			case 1:
				int edmg = eatk - pdef;
				if(edmg <= 0) return 0;
				else {
					php -= edmg;
					accumulate += edmg;
					turn = 0;
					break;
				}
			}
			if(accumulate >= player.get("stat.hp")) return -1;
			if(ehp <= 0 || php <= 0) canend = true;
		}
		return accumulate;
	}

}
