
package com.shatteredpixel.shatteredpixeldungeon.levels.painters;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RotHeart;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RotLasher;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.watabou.utils.Random;

public class RotGardenPainter extends Painter {

	public static void paint( Level level, Room room ) {

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.depth));

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.GRASS);


		int heartX = Random.IntRange(room.left+1, room.right-1);
		int heartY = Random.IntRange(room.top+1, room.bottom-1);

		if (entrance.x == room.left) {
			heartX = room.right - 1;
		} else if (entrance.x == room.right) {
			heartX = room.left + 1;
		} else if (entrance.y == room.top) {
			heartY = room.bottom - 1;
		} else if (entrance.y == room.bottom) {
			heartY = room.top + 1;
		}

		placePlant(level, heartX + heartY * Level.WIDTH, new RotHeart());

		int lashers = ((room.right-room.left-1)*(room.bottom-room.top-1))/8;

		for (int i = 1; i <= lashers; i++){
			int pos;
			do {
				pos = room.random();
			} while (!validPlantPos(level, pos));
			placePlant(level, pos, new RotLasher());
		}
	}

	private static boolean validPlantPos(Level level, int pos){
		if (level.map[pos] != Terrain.GRASS){
			return false;
		}

		for (int i : Level.NEIGHBOURS9){
			if (level.findMob(pos+i) != null){
				return false;
			}
		}

		return true;
	}

	private static void placePlant(Level level, int pos, Mob plant){
		plant.pos = pos;
		level.mobs.add( plant );

		for(int i : Level.NEIGHBOURS8) {
			if (level.map[pos + i] == Terrain.GRASS){
				set(level, pos + i, Terrain.HIGH_GRASS);
			}
		}
	}
}
