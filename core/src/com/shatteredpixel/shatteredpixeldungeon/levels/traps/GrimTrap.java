
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class GrimTrap extends Trap {

	{
		color = TrapSprite.GREY;
		shape = TrapSprite.LARGE_DOT;
	}

	@Override
	public Trap hide() {
		//cannot hide this trap
		return reveal();
	}

	@Override
	public void activate() {
		Char target = Actor.findChar(pos);

		//find the closest char that can be aimed at
		if (target == null){
			for (Char ch : Actor.chars()){
				Ballistica bolt = new Ballistica(pos, ch.pos, Ballistica.PROJECTILE);
				if (bolt.collisionPos == ch.pos &&
						(target == null || Level.distance(pos, ch.pos) < Level.distance(pos, target.pos))){
					target = ch;
				}
			}
		}

		if (target != null){
			final Char finalTarget = target;
			final GrimTrap trap = this;
			MagicMissile.shadow(target.sprite.parent, pos, target.pos, new Callback() {
				@Override
				public void call() {

					if (finalTarget == Dungeon.hero) {
						//almost kill the player
						if (((float)finalTarget.HP/finalTarget.HT) >= 0.9f){
							finalTarget.damage((finalTarget.HP-1), trap);
						//kill 'em
						} else {
							finalTarget.damage(finalTarget.HP, trap);
						}
						Sample.INSTANCE.play(Assets.SND_CURSED);
						if (!finalTarget.isAlive()) {
							Dungeon.fail( GrimTrap.class );
							GLog.n( Messages.get(GrimTrap.class, "ondeath") );
						}
					} else {
						finalTarget.damage(finalTarget.HP, this);
						Sample.INSTANCE.play(Assets.SND_BURNING);
					}
					finalTarget.sprite.emitter().burst(ShadowParticle.UP, 10);
					if (!finalTarget.isAlive()) finalTarget.next();
				}
			});
		} else {
			CellEmitter.get(pos).burst(ShadowParticle.UP, 10);
			Sample.INSTANCE.play(Assets.SND_BURNING);
		}
	}
}
