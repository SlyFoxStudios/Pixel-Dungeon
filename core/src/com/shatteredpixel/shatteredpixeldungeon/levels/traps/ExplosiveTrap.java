
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.items.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;

public class ExplosiveTrap extends Trap {

	{
		color = TrapSprite.ORANGE;
		shape = TrapSprite.DIAMOND;
	}

	@Override
	public void activate() {
		new Bomb().explode(pos);
	}

}
