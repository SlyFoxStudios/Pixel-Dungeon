
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Fire;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ElementalSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Elemental extends Mob {

	{
		spriteClass = ElementalSprite.class;

		HP = HT = 65;
		defenseSkill = 20;

		EXP = 10;
		maxLvl = 24;

		flying = true;

		loot = new PotionOfLiquidFlame();
		lootChance = 0.1f;

		properties.add(Property.DEMONIC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 16, 20 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 25;
	}

	@Override
	public int dr() {
		return 5;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		if (Random.Int( 2 ) == 0) {
			Buff.affect( enemy, Burning.class ).reignite( enemy );
		}

		return damage;
	}

	@Override
	public void add( Buff buff ) {
		if (buff instanceof Burning) {
			if (HP < HT) {
				HP++;
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			}
		} else if (buff instanceof Frost || buff instanceof Chill) {
				if (Level.water[this.pos])
					damage( Random.NormalIntRange( HT / 2, HT ), buff );
				else
					damage( Random.NormalIntRange( 1, HT * 2 / 3 ), buff );
		} else {
			super.add( buff );
		}
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Burning.class );
		IMMUNITIES.add( Fire.class );
		IMMUNITIES.add( WandOfFireblast.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
