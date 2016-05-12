
package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Random;

public class Drowsy extends Buff {

	{
		type = buffType.NEUTRAL;
	}

	@Override
	public int icon() {
		return BuffIndicator.DROWSY;
	}

	public boolean attachTo( Char target ) {
		if (!target.immunities().contains(Sleep.class) && super.attachTo(target)) {
			if (cooldown() == 0)
				spend(Random.Int(3, 6));
			return true;
		}
		return false;
	}

	@Override
	public boolean act(){
			Buff.affect(target, MagicalSleep.class);

			detach();
			return true;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(cooldown()+1));
	}
}
