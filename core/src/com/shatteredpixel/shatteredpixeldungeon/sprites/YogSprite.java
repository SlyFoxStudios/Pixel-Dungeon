
package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.watabou.noosa.TextureFilm;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;

public class YogSprite extends MobSprite {
	
	public YogSprite() {
		super();
		
		texture( Assets.YOG );
		
		TextureFilm frames = new TextureFilm( texture, 20, 19 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1, 2, 2, 1, 0, 3, 4, 4, 3, 0, 5, 6, 6, 5 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 0 );
		
		die = new Animation( 10, false );
		die.frames( frames, 0, 7, 8, 9 );
		
		play( idle );
	}
	
	@Override
	public void die() {
		super.die();
		
		Splash.at( center(), blood(), 12 );
	}
}
