
package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Arrays;

public class LastLevel extends Level {

	private static final int SIZE = 30;

	{
		color1 = 0x801500;
		color2 = 0xa68521;

		viewDistance = 8;
	}

	private int pedestal;

	@Override
	public String tilesTex() {
		return Assets.TILES_HALLS;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_HALLS;
	}

	@Override
	public void create() {
		super.create();
		for (int i=0; i < LENGTH; i++) {
			int flags = Terrain.flags[map[i]];
			if ((flags & Terrain.PIT) != 0){
				passable[i] = avoid[i] = false;
				solid[i] = true;
			}
		}
	}

	@Override
	protected boolean build() {

		Arrays.fill( map, Terrain.CHASM );

		Painter.fill( this, 7, 31, 19, 1, Terrain.WALL );
		Painter.fill( this, 15, 10, 3, 21, Terrain.EMPTY);
		Painter.fill( this, 13, 30, 7, 1, Terrain.EMPTY);
		Painter.fill( this, 14, 29, 5, 1, Terrain.EMPTY);

		Painter.fill( this, 14, 9, 5, 7, Terrain.EMPTY);
		Painter.fill( this, 13, 10, 7, 5, Terrain.EMPTY);

		//Painter.fill( this, 2, 2, SIZE-2, SIZE-2, Terrain.EMPTY );
		//Painter.fill( this, SIZE/2, SIZE/2, 3, 3, Terrain.EMPTY_SP );

		entrance = SIZE * WIDTH + SIZE / 2 + 1;
		map[entrance] = Terrain.ENTRANCE;

		pedestal = (SIZE / 2 + 1) * (WIDTH + 1) - 4*WIDTH;
		map[pedestal] = Terrain.PEDESTAL;
		map[pedestal-1-WIDTH] = map[pedestal+1-WIDTH] = map[pedestal-1+WIDTH] = map[pedestal+1+WIDTH] = Terrain.STATUE_SP;

		exit = pedestal;

		int pos = pedestal;

		map[pos-WIDTH] = map[pos-1] = map[pos+1] = map[pos-2] = map[pos+2] = Terrain.WATER;
		pos+=WIDTH;
		map[pos] = map[pos-2] = map[pos+2] = map[pos-3] = map[pos+3] = Terrain.WATER;
		pos+=WIDTH;
		map[pos-3] = map[pos-2] = map[pos-1] = map[pos] = map[pos+1] = map[pos+2] = map[pos+3] = Terrain.WATER;
		pos+=WIDTH;
		map[pos-2] = map[pos+2] = Terrain.WATER;


		feeling = Feeling.NONE;
		viewDistance = 8;

		return true;
	}

	@Override
	protected void decorate() {
		for (int i=0; i < LENGTH; i++) {
			if (map[i] == Terrain.EMPTY && Random.Int( 10 ) == 0) {
				map[i] = Terrain.EMPTY_DECO;
			}
		}
	}

	@Override
	protected void createMobs() {
	}

	public Actor respawner() {
		return null;
	}

	@Override
	protected void createItems() {
		drop( new Amulet(), pedestal );
	}

	@Override
	public int randomRespawnCell() {
		int cell = entrance + NEIGHBOURS8[Random.Int(8)];
		while (!passable[cell]){
			cell = entrance + NEIGHBOURS8[Random.Int(8)];
		}
		return cell;
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_name");
			case Terrain.GRASS:
				return Messages.get(HallsLevel.class, "grass_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(HallsLevel.class, "high_grass_name");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(HallsLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public Group addVisuals () {
		super.addVisuals();
		HallsLevel.addHallsVisuals(this, visuals);
		return visuals;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		for (int i=0; i < LENGTH; i++) {
			int flags = Terrain.flags[map[i]];
			if ((flags & Terrain.PIT) != 0){
				passable[i] = avoid[i] = false;
				solid[i] = true;
			}
		}
	}
}
