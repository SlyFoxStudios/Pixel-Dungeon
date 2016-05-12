
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Leech;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ScorpioSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Scorpio extends Mob {

	{
		spriteClass = ScorpioSprite.class;

		HP = HT = 95;
		defenseSkill = 24;
		viewDistance = Light.DISTANCE;

		EXP = 14;
		maxLvl = 30;

		loot = new PotionOfHealing();
		lootChance = 0.2f;

		properties.add(Property.DEMONIC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 20, 32 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 36;
	}

	@Override
	public int dr() {
		return 16;
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		if (Random.Int( 2 ) == 0) {
			Buff.prolong( enemy, Cripple.class, Cripple.DURATION );
		}

		return damage;
	}

	@Override
	protected boolean getCloser( int target ) {
		if (state == HUNTING) {
			return enemySeen && getFurther( target );
		} else {
			return super.getCloser( target );
		}
	}

	@Override
	protected Item createLoot() {
		//5/count+5 total chance of getting healing, failing the 2nd roll drops mystery meat instead.
		if (Random.Int( 5 + Dungeon.limitedDrops.scorpioHP.count ) <= 4) {
			Dungeon.limitedDrops.scorpioHP.count++;
			return (Item)loot;
		} else {
			return new MysteryMeat();
		}
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<>();
	static {
		RESISTANCES.add( Leech.class );
		RESISTANCES.add( Poison.class );
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
