
package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;

public class Luck extends Weapon.Enchantment {

	private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing( 0x00FF00 );
	
	@Override
	public boolean proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		int level = Math.max( 0, weapon.level() );
		
		int dmg = damage;
		for (int i=1; i <= level+1; i++) {
			dmg = Math.max( dmg, attacker.damageRoll() - i );
		}
		
		if (dmg > damage) {
			defender.damage( dmg - damage, this );
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Glowing glowing() {
		return GREEN;
	}
}
