
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatSprite;
import com.watabou.utils.Random;

public class Rat extends Mob {

	{
		spriteClass = RatSprite.class;

		HP = HT = 8;
		defenseSkill = 2;

		maxLvl = 6;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 4 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 8;
	}

	@Override
	public int dr() {
		return 1;
	}
}
