
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShieldedSprite;

public class Shielded extends Brute {

	{
		spriteClass = ShieldedSprite.class;
		
		defenseSkill = 20;
	}
	
	@Override
	public int dr() {
		return 10;
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );
		Badges.validateRare( this );
	}
}
