
package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Knuckles extends MeleeWeapon {

	{
		image = ItemSpriteSheet.KNUCKLEDUSTER;
	}
	
	public Knuckles() {
		super( 1, 1f, 0.5f );
	}

}
