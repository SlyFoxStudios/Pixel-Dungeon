
package com.shatteredpixel.shatteredpixeldungeon.items.rings;


public class RingOfMight extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Might();
	}

	public class Might extends RingBuff {
	}
}

