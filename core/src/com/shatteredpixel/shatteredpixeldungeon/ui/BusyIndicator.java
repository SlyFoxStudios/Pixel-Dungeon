
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.watabou.noosa.Image;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;

public class BusyIndicator extends Image {
	
	public BusyIndicator() {
		super();
		copy( Icons.BUSY.get() );
		
		origin.set( width / 2, height / 2 );
		angularSpeed = 720;
	}
	
	@Override
	public void update() {
		super.update();
		visible = Dungeon.hero.isAlive() && !Dungeon.hero.ready;
	}
}
