
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GnollSprite;
import com.watabou.utils.Random;

public class Gnoll extends Mob {

	{
		spriteClass = GnollSprite.class;

		HP = HT = 12;
		defenseSkill = 4;

		EXP = 2;
		maxLvl = 10;

		loot = Gold.class;
		lootChance = 0.5f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 2, 5 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 10;
	}

	@Override
	public int dr() {
		return 2;
	}
}
