
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfTenacity extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Tenacity();
	}

	public class Tenacity extends RingBuff {
	}
}

