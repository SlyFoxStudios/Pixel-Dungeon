
package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class StenchGas extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		Char ch;
		for (int i=0; i < LENGTH; i++) {
			if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
				if (!ch.immunities().contains(this.getClass()))
					Buff.prolong( ch, Paralysis.class, Paralysis.duration( ch )/5 );
			}
		}
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );

		emitter.pour( Speck.factory(Speck.STENCH), 0.6f );
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
