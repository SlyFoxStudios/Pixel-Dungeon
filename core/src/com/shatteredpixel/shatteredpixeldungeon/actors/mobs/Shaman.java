
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.LightningTrap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShamanSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Shaman extends Mob implements Callback {

	private static final float TIME_TO_ZAP	= 1f;

	{
		spriteClass = ShamanSprite.class;

		HP = HT = 18;
		defenseSkill = 8;

		EXP = 6;
		maxLvl = 17;

		loot = Generator.Category.SCROLL;
		lootChance = 0.33f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 2, 6 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 11;
	}

	@Override
	public int dr() {
		return 4;
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack( Char enemy ) {

		if (Level.distance( pos, enemy.pos ) <= 1) {

			return super.doAttack( enemy );

		} else {

			boolean visible = Level.fieldOfView[pos] || Level.fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap( enemy.pos );
			}

			spend( TIME_TO_ZAP );

			if (hit( this, enemy, true )) {
				int dmg = (int)(damageRoll()*1.5f);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage( dmg, LightningTrap.LIGHTNING );

				enemy.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake( 2, 0.3f );

					if (!enemy.isAlive()) {
						Dungeon.fail( getClass() );
						GLog.n( Messages.get(this, "zap_kill") );
					}
				}
			} else {
				enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
			}

			return !visible;
		}
	}

	@Override
	public void call() {
		next();
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<>();
	static {
		RESISTANCES.add( LightningTrap.Electricity.class );
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
