
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class WornTrap extends Trap {

	{
		color = TrapSprite.BLACK;
		shape = TrapSprite.DOTS;
	}

	@Override
	public Trap hide() {
		//this one can't be hidden
		return reveal();
	}

	@Override
	public void activate() {
		GLog.i( Messages.get(this, "nothing") );
	}
}
