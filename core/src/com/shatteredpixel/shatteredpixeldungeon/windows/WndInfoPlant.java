
package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PlantSprite;

public class WndInfoPlant extends WndTitledMessage {
	
	public WndInfoPlant( Plant plant ) {
		
		super(new PlantSprite( plant.image ), plant.plantName, plant.desc());

	}
}
