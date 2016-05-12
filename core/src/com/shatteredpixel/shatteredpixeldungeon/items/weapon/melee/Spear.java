
package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Spear extends MeleeWeapon {

	{
		image = ItemSpriteSheet.SPEAR;
	}

	@Override
	public int reachFactor(Hero hero) {
		return 2;
	}

	public Spear() {
		super( 2, 1f, 1.5f );
	}

}
