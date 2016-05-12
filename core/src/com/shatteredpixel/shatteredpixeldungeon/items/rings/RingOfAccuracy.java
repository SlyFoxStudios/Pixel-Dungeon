
package com.shatteredpixel.shatteredpixeldungeon.items.rings;

public class RingOfAccuracy extends Ring {
	
	@Override
	protected RingBuff buff( ) {
		return new Accuracy();
	}
	
	public class Accuracy extends RingBuff {
	}
}
