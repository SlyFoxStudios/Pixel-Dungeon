
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;
import com.watabou.noosa.audio.Sample;

public class BlazingTrap extends Trap {

	{
		color = TrapSprite.ORANGE;
		shape = TrapSprite.STARS;
	}


	@Override
	public void activate() {
		for (int i : Level.NEIGHBOURS9DIST2){
			if (Level.insideMap(pos+i) && !Level.solid[pos+i]) {
				if (Level.pit[pos+i] || Level.water[pos+i])
					GameScene.add(Blob.seed(pos + i, 1, Fire.class));
				else
					GameScene.add(Blob.seed(pos + i, 5, Fire.class));
				CellEmitter.get(pos + i).burst(FlameParticle.FACTORY, 5);
			}
		}
		Sample.INSTANCE.play(Assets.SND_BURNING);
	}
}
