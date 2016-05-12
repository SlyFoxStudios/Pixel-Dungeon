
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Leech;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SuccubusSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class Succubus extends Mob {

	private static final int BLINK_DELAY	= 5;

	private int delay = 0;

	{
		spriteClass = SuccubusSprite.class;

		HP = HT = 80;
		defenseSkill = 25;
		viewDistance = Light.DISTANCE;

		EXP = 12;
		maxLvl = 30;

		loot = new ScrollOfLullaby();
		lootChance = 0.05f;

		properties.add(Property.DEMONIC);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 15, 25 );
	}

	@Override
	public int attackProc( Char enemy, int damage ) {

		if (Random.Int( 3 ) == 0) {
			Buff.affect( enemy, Charm.class, Charm.durationFactor( enemy ) * Random.IntRange( 3, 7 ) ).object = id();
			enemy.sprite.centerEmitter().start( Speck.factory( Speck.HEART ), 0.2f, 5 );
			Sample.INSTANCE.play( Assets.SND_CHARMS );
		}

		return damage;
	}

	@Override
	protected boolean getCloser( int target ) {
		if (Level.fieldOfView[target] && Level.distance( pos, target ) > 2 && delay <= 0) {

			blink( target );
			spend( -1 / speed() );
			return true;

		} else {

			delay--;
			return super.getCloser( target );

		}
	}

	private void blink( int target ) {

		Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
		int cell = route.collisionPos;

		//can't occupy the same cell as another char, so move back one.
		if (Actor.findChar( cell ) != null && cell != this.pos)
			cell = route.path.get(route.dist-1);

		if (Level.avoid[ cell ]){
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : Level.NEIGHBOURS8) {
				cell = route.collisionPos + n;
				if (Level.passable[cell] && Actor.findChar( cell ) == null) {
					candidates.add( cell );
				}
			}
			if (candidates.size() > 0)
				cell = Random.element(candidates);
			else {
				delay = BLINK_DELAY;
				return;
			}
		}

		ScrollOfTeleportation.appear( this, cell );

		delay = BLINK_DELAY;
	}

	@Override
	public int attackSkill( Char target ) {
		return 40;
	}

	@Override
	public int dr() {
		return 10;
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<>();
	static {
		RESISTANCES.add( Leech.class );
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( Sleep.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
