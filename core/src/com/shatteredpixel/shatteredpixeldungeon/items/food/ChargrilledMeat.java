
package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ChargrilledMeat extends Food {

	{
		image = ItemSpriteSheet.STEAK;
		energy = Hunger.STARVING - Hunger.HUNGRY;
		hornValue = 1;
	}
	
	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public static Food cook( MysteryMeat ingredient ) {
		ChargrilledMeat result = new ChargrilledMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
}
