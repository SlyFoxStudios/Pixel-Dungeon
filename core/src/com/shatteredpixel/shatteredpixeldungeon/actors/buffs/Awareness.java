
package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;

public class Awareness extends FlavourBuff {

	public static final float DURATION = 2f;

	@Override
	public void detach() {
		super.detach();
		Dungeon.observe();
	}
}
