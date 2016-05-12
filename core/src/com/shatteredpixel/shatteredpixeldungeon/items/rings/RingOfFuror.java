
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfFuror extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Furor();
	}

	public class Furor extends RingBuff {
	}
}
