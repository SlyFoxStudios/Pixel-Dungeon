
package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Mace extends MeleeWeapon {

	{
		image = ItemSpriteSheet.MACE;
	}
	
	public Mace() {
		super( 3, 1f, 0.8f );
	}

}
