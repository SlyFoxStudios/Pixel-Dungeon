
package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Tamahawk extends MissileWeapon {

	{
		image = ItemSpriteSheet.TOMAHAWK;
		
		STR = 17;
	}

	@Override
	public int min() {
		return 4;
	}

	@Override
	public int max() {
		return 20;
	}

	public Tamahawk() {
		this( 1 );
	}
	
	public Tamahawk( int number ) {
		super();
		quantity = number;
	}
	
	@Override
	public void proc( Char attacker, Char defender, int damage ) {
		super.proc( attacker, defender, damage );
		Buff.affect( defender, Bleeding.class ).set( damage );
	}
	
	@Override
	public Item random() {
		quantity = Random.Int( 5, 12 );
		return this;
	}
	
	@Override
	public int price() {
		return 15 * quantity;
	}
}
