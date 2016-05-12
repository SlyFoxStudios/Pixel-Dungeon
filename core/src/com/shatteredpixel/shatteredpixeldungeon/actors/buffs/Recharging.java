
package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class Recharging extends FlavourBuff {

	@Override
	public int icon() {
		return BuffIndicator.RECHARGING;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	//want to process partial turns for this buff, and not count it when it's expiring.
	//firstly, if this buff has half a turn left, should give out half the benefit.
	//secondly, recall that buffs execute in random order, so this can cause a problem where we can't simply check
	//if this buff is still attached, must instead directly check its remaining time, and act accordingly.
	//otherwise this causes inconsistent behaviour where this may detach before, or after, a wand charger acts.
	public float remainder() {
		return Math.min(1f, this.cooldown());
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}
}
