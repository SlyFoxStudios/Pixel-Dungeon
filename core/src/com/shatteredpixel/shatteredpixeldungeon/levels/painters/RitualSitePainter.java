
package com.shatteredpixel.shatteredpixeldungeon.levels.painters;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CeremonialCandle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.CustomTileVisual;
import com.watabou.utils.Point;

public class RitualSitePainter extends Painter {

	public static void paint( Level level, Room room) {

		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.REGULAR );
		}

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		RitualMarker vis = new RitualMarker();
		Point c = room.center();
		vis.pos(c.x - 1, c.y - 1);

		level.customTiles.add(vis);

		fill(level, c.x-1, c.y-1, 3, 3, Terrain.EMPTY_DECO);

		level.addItemToSpawn(new CeremonialCandle());
		level.addItemToSpawn(new CeremonialCandle());
		level.addItemToSpawn(new CeremonialCandle());
		level.addItemToSpawn(new CeremonialCandle());

		CeremonialCandle.ritualPos = c.x + (Level.WIDTH * c.y);
	}

	public static class RitualMarker extends CustomTileVisual{

		{
			name = Messages.get(this, "name");

			tx = Assets.PRISON_QUEST;
			txX = txY = 0;
			tileW = tileH = 3;
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}
	}

}
