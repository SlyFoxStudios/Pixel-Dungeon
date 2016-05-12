
package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class Regrowth extends Blob {
	
	@Override
	protected void evolve() {
		super.evolve();
		
		if (volume > 0) {
			
			for (int i=0; i < LENGTH; i++) {
				if (off[i] > 0) {
					int c = Dungeon.level.map[i];
					int c1 = c;
					if (c == Terrain.EMPTY || c == Terrain.EMBERS || c == Terrain.EMPTY_DECO) {
						c1 = cur[i] > 9 ? Terrain.HIGH_GRASS : Terrain.GRASS;
					} else if (c == Terrain.GRASS && cur[i] > 9 && Dungeon.level.plants.get(i) == null ) {
						c1 = Terrain.HIGH_GRASS;
					}

					if (c1 != c) {
						Level.set( i, Terrain.HIGH_GRASS );
						Dungeon.observe();

						GameScene.updateMap( i );
						if (Dungeon.visible[i]) {
							GameScene.discoverTile( i, c );
						}
					}
					
					Char ch = Actor.findChar( i );
					if (ch != null && cur[i] > 1) {
						Buff.prolong( ch, Roots.class, TICK );
					}
				}
			}
		}
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.start( LeafParticle.LEVEL_SPECIFIC, 0.2f, 0 );
	}
}
