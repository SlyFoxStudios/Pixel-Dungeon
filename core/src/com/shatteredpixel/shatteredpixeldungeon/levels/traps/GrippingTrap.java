
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;
import com.watabou.utils.Random;

public class GrippingTrap extends Trap {

	{
		color = TrapSprite.GREY;
		shape = TrapSprite.CROSSHAIR;
	}

	@Override
	public void activate() {

		Char c = Actor.findChar( pos );

		if (c != null) {
			int damage = Math.max( 0,  (Dungeon.depth) - Random.IntRange( 0, c.dr() / 2 ) );
			Buff.affect( c, Bleeding.class ).set( damage );
			Buff.prolong( c, Cripple.class, 15f);
			Buff.prolong( c, Roots.class, 5f);
			Wound.hit( c );
		} else {
			Wound.hit( pos );
		}

	}
}
