
package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Sword extends MeleeWeapon {
	
	{
		image = ItemSpriteSheet.SWORD;
	}
	
	public Sword() {
		super( 3, 1f, 1f );
	}

}
