
package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class MasterThievesArmband extends Artifact {

	{
		image = ItemSpriteSheet.ARTIFACT_ARMBAND;

		levelCap = 10;

		charge = 0;
	}

	private int exp = 0;

	@Override
	protected ArtifactBuff passiveBuff() {
		return new Thievery();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if ( isEquipped (Dungeon.hero) )
			desc += "\n\n" + Messages.get(this, "desc_worn");

		return desc;
	}

	public class Thievery extends ArtifactBuff{
		public void collect(int gold){
			charge += gold/2;
		}

		@Override
		public void detach() {
			charge *= 0.95;
			super.detach();
		}

		public boolean steal(int value){
			if (value <= charge){
				charge -= value;
				exp += value;
			} else {
				float chance = stealChance(value);
				if (Random.Float() > chance)
					return false;
				else {
					if (chance <= 1)
						charge = 0;
					else
						//removes the charge it took you to reach 100%
						charge -= charge/chance;
					exp += value;
				}
			}
			while(exp >= 600 && level() < levelCap) {
				exp -= 600;
				upgrade();
			}
			return true;
		}

		public float stealChance(int value){
				//get lvl*100 gold or lvl*5% item value of free charge, whichever is less.
				int chargeBonus = Math.min(level()*100, (value*level())/20);

				return (((float)charge + chargeBonus)/value);
		}
	}
}
