
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NewbornElementalSprite;

public class NewbornElemental extends Elemental {

	{
		spriteClass = NewbornElementalSprite.class;

		HT = 65;
		HP = HT/2; //32

		defenseSkill = 12;

		EXP = 7;

		properties.add(Property.MINIBOSS);
	}

	@Override
	public int damageRoll() {
		return super.damageRoll()/2;
	} //8-10

	@Override
	public void add(Buff buff) {
		if (buff instanceof Frost || buff instanceof Chill) {
			die(buff);
		} else {
			super.add(buff);
		}
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Dungeon.level.drop( new Embers(), pos ).sprite.drop();
	}
}
