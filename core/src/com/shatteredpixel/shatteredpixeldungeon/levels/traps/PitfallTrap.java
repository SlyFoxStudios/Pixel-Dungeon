
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.WindParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;

public class PitfallTrap extends Trap {

	{
		color = TrapSprite.RED;
		shape = TrapSprite.DIAMOND;
	}

	@Override
	public void activate() {
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap != null){
			for (Item item : heap.items){
				Dungeon.dropToChasm(item);
			}
			heap.sprite.kill();
			GameScene.discard(heap);
			Dungeon.level.heaps.remove( pos );
		}

		Char ch = Actor.findChar( pos );

		if (ch == Dungeon.hero){
			Chasm.heroFall( pos );
		} else if (ch != null){
			Chasm.mobFall((Mob)ch);
		}
	}

	@Override
	protected void disarm() {
		super.disarm();

		//if making a pit here wouldn't block any paths, make a pit tile instead of a disarmed trap tile.
		if (!(Dungeon.level.solid[pos - Level.WIDTH] && Dungeon.level.solid[pos + Level.WIDTH])
				&& !(Dungeon.level.solid[pos - 1]&& Dungeon.level.solid[pos + 1])){

			int c = Dungeon.level.map[pos - Level.WIDTH];

			if (c == Terrain.WALL || c == Terrain.WALL_DECO) {
				Level.set(pos, Terrain.CHASM_WALL);
			} else {
				Level.set( pos, Terrain.CHASM_FLOOR );
			}

			sprite.parent.add(new WindParticle.Wind(pos));
			sprite.kill();
			GameScene.updateMap( pos );
		}
	}
}
