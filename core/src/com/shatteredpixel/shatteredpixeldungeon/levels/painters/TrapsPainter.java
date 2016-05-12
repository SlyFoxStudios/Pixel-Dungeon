
package com.shatteredpixel.shatteredpixeldungeon.levels.painters;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.*;
import com.watabou.utils.Random;

public class TrapsPainter extends Painter {

	public static void paint( Level level, Room room ) {
		 
		fill( level, room, Terrain.WALL );

		Class<? extends Trap> trapClass;
		switch (Random.Int(5)){
			case 0: default:
				trapClass = SpearTrap.class;
				break;
			case 1:
				trapClass = !Dungeon.bossLevel(Dungeon.depth + 1)? null : SummoningTrap.class;
				break;
			case 2: case 3: case 4:
				trapClass = Random.oneOf(levelTraps[Dungeon.depth/5]);
				break;
		}

		if (trapClass == null){
			fill(level, room, 1, Terrain.CHASM);
		} else {
			fill(level, room, 1, Terrain.TRAP);
		}
		
		Room.Door door = room.entrance();
		door.set( Room.Door.Type.REGULAR );
		
		int lastRow = level.map[room.left + 1 + (room.top + 1) * Level.WIDTH] == Terrain.CHASM ? Terrain.CHASM : Terrain.EMPTY;

		int x = -1;
		int y = -1;
		if (door.x == room.left) {
			x = room.right - 1;
			y = room.top + room.height() / 2;
			fill( level, x, room.top + 1, 1, room.height() - 1 , lastRow );
		} else if (door.x == room.right) {
			x = room.left + 1;
			y = room.top + room.height() / 2;
			fill( level, x, room.top + 1, 1, room.height() - 1 , lastRow );
		} else if (door.y == room.top) {
			x = room.left + room.width() / 2;
			y = room.bottom - 1;
			fill( level, room.left + 1, y, room.width() - 1, 1 , lastRow );
		} else if (door.y == room.bottom) {
			x = room.left + room.width() / 2;
			y = room.top + 1;
			fill( level, room.left + 1, y, room.width() - 1, 1 , lastRow );
		}

		for(int cell : room.getCells()) {
			if (level.map[cell] == Terrain.TRAP){
				try {
					level.setTrap(((Trap) trapClass.newInstance()).reveal(), cell);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		int pos = x + y * Level.WIDTH;
		if (Random.Int( 3 ) == 0) {
			if (lastRow == Terrain.CHASM) {
				set( level, pos, Terrain.EMPTY );
			}
			level.drop( prize( level ), pos ).type = Heap.Type.CHEST;
		} else {
			set( level, pos, Terrain.PEDESTAL );
			level.drop( prize( level ), pos );
		}
		
		level.addItemToSpawn( new PotionOfLevitation() );
	}
	
	private static Item prize( Level level ) {

		Item prize;

		if (Random.Int(4) != 0){
			prize = level.findPrizeItem();
			if (prize != null)
				return prize;
		}
		
		prize = Generator.random( Random.oneOf(
			Generator.Category.WEAPON,
			Generator.Category.ARMOR
		) );

		for (int i=0; i < 3; i++) {
			Item another = Generator.random( Random.oneOf(
				Generator.Category.WEAPON,
				Generator.Category.ARMOR
			) );
			if (another.level() > prize.level()) {
				prize = another;
			}
		}
		
		return prize;
	}

	@SuppressWarnings("unchecked")
	private static Class<?extends Trap>[][] levelTraps = new Class[][]{
			//sewers
			{ToxicTrap.class, TeleportationTrap.class, FlockTrap.class},
			//prison
			{ConfusionTrap.class, ExplosiveTrap.class, ParalyticTrap.class},
			//caves
			{BlazingTrap.class, VenomTrap.class, ExplosiveTrap.class},
			//city
			{WarpingTrap.class, VenomTrap.class, DisintegrationTrap.class},
			//halls, muahahahaha
			{GrimTrap.class}
	};
}
