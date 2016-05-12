
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfSharpshooting extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Aim();
	}

	public class Aim extends RingBuff {
	}
}
