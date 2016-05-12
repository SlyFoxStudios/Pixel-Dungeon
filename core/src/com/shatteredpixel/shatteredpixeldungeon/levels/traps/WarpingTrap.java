
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WarpingTrap extends Trap {

	{
		color = TrapSprite.TEAL;
		shape = TrapSprite.STARS;
	}

	@Override
	public void activate() {
		CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play( Assets.SND_TELEPORT );

		if (Dungeon.depth > 1 && !Dungeon.bossLevel()) {

			//each depth has 1 more weight than the previous depth.
			float[] depths = new float[Dungeon.depth-1];
			for (int i = 1; i < Dungeon.depth; i++) depths[i-1] = i;
			int depth = 1+Random.chances(depths);

			Heap heap = Dungeon.level.heaps.get(pos);
			if (heap != null) {
				ArrayList<Item> dropped = Dungeon.droppedItems.get( depth );
				if (dropped == null) {
					Dungeon.droppedItems.put( depth, dropped = new ArrayList<Item>() );
				}
				for (Item item : heap.items){
					dropped.add(item);
				}
				heap.destroy();
			}

			Char ch = Actor.findChar( pos );
			if (ch == Dungeon.hero){
				Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null) buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] ))
					if (mob instanceof DriedRose.GhostHero) mob.destroy();

				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = depth;
				InterlevelScene.returnPos = -1;
				Game.switchScene(InterlevelScene.class);
			} else if (ch != null) {
				ch.destroy();
				ch.sprite.killAndErase();
				Dungeon.level.mobs.remove(ch);
			}

		}

	}
}
