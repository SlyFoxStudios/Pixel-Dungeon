
package com.shatteredpixel.shatteredpixeldungeon.items.potions;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;

public class PotionOfExperience extends Potion {

	{
		initials = 0;

		bones = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		setKnown();
		hero.earnExp( hero.maxExp() );
	}
	
	@Override
	public int price() {
		return isKnown() ? 80 * quantity : super.price();
	}
}
