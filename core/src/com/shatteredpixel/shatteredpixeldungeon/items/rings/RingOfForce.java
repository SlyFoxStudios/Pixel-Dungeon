
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class RingOfForce extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Force();
	}

	public static int min(int bonus, int herostr){
		if (bonus < 0) return 0;
		int STR = herostr-8;
		return Math.max(STR/2+bonus, 0);
	}

	public static int max(int bonus, int herostr){
		if (bonus < 0) return 0;
		int STR = herostr-8;
		return Math.max((int)(STR*0.5f*bonus) + STR*2, bonus);
	}

	@Override
	public String desc() {
		String desc = super.desc();
		int str = Dungeon.hero.STR();
		if (levelKnown) {
			desc += "\n\n" + Messages.get(this, "avg_dmg", (min(level(), str) + max(level(), str))/2);
		} else {
			desc += "\n\n" + Messages.get(this, "typical_avg_dmg", (min(1, str) + max(1, str))/2);
		}

		return desc;
	}

	public class Force extends RingBuff {
	}
}

