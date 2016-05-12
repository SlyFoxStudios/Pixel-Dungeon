
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfWealth extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Wealth();
	}

	public class Wealth extends RingBuff {
	}
}
