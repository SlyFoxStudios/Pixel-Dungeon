
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfMagic extends Ring {
	
	@Override
	protected RingBuff buff( ) {
		return new Magic();
	}
	
	public class Magic extends RingBuff {
	}
}
