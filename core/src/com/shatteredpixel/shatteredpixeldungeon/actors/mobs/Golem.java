
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GolemSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Golem extends Mob {

	{
		spriteClass = GolemSprite.class;

		HP = HT = 85;
		defenseSkill = 18;

		EXP = 12;
		maxLvl = 27;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 20, 40 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 28;
	}

	@Override
	protected float attackDelay() {
		return 1.5f;
	}

	@Override
	public int dr() {
		return 12;
	}

	@Override
	public void die( Object cause ) {
		Imp.Quest.process( this );

		super.die( cause );
	}
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<>();
	static {
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Amok.class );
		IMMUNITIES.add( Terror.class );
		IMMUNITIES.add( Sleep.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
