
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.VenomGas;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;

public class VenomTrap extends Trap {

	{
		color = TrapSprite.VIOLET;
		shape = TrapSprite.GRILL;
	}

	@Override
	public void activate() {

		VenomGas venomGas = Blob.seed(pos, 80 + 5 * Dungeon.depth, VenomGas.class);

		venomGas.setStrength(1+Dungeon.depth/4);

		GameScene.add(venomGas);

	}
}
