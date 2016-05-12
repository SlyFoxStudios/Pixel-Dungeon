
package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;


//TODO: balancing
public class WandOfCorruption extends Wand {

	{
		image = ItemSpriteSheet.WAND_CORRUPTION;
	}

	@Override
	protected void onZap(Ballistica bolt) {
		Char ch = Actor.findChar(bolt.collisionPos);

		if (ch != null){

			if(ch.buff(Corruption.class) != null){
				GLog.w( Messages.get(this, "already_corrupted") );
				return;
			}

			if (ch.properties().contains(Char.Property.BOSS) || ch.properties().contains(Char.Property.MINIBOSS)){
				GLog.w( Messages.get(this, "boss") );
				return;
			}

			int basePower = 10 + 2*level();
			int mobPower = Random.IntRange(0, ch.HT) + ch.HP*2;
			for ( Buff buff : ch.buffs()){
				if (buff.type == Buff.buffType.NEGATIVE){
					mobPower *= 0.67;
					break;
				}
			}

			int extraCharges = 0;
			//try to use extra charges to overpower the mob
			while (basePower <= mobPower){
				extraCharges++;
				basePower += 5 + level();
			}

			//if we fail, lose all charges, remember we have 1 left to lose from using the wand.
			if (extraCharges >= curCharges){
				curCharges = 1;
				GLog.w( Messages.get(this, "fail") );
				return;
			}

			//otherwise corrupt the mob & spend charges
			Buff.append(ch, Corruption.class);
			ch.HP = ch.HT;
			curCharges -= extraCharges;
			usagesToKnow -= extraCharges;

			processSoulMark(ch, extraCharges+chargesPerCast());
		}
	}

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		// lvl 0 - 25%
		// lvl 1 - 40%
		// lvl 2 - 50%
		if (Random.Int( level() + 4 ) >= 3){
			Buff.prolong( defender, Amok.class, 3+level());
		}
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.shadow(curUser.sprite.parent, bolt.sourcePos, bolt.collisionPos, callback);
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}

	@Override
	public void staffFx(MagesStaff.StaffParticle particle) {
		particle.color( 0 );
		particle.am = 0.6f;
		particle.setLifespan(0.8f);
		particle.acc.set(0, 20);
		particle.setSize( 0f, 3f);
		particle.shuffleXY(2f);
	}

}
