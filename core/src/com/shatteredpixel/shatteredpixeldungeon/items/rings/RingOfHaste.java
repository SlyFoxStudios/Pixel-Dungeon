
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfHaste extends Ring {
	
	@Override
	protected RingBuff buff( ) {
		return new Haste();
	}
	
	public class Haste extends RingBuff {
	}
}
