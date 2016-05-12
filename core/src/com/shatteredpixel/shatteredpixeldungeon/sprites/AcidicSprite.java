
package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.watabou.noosa.TextureFilm;
import com.shatteredpixel.shatteredpixeldungeon.Assets;

public class AcidicSprite extends ScorpioSprite {
	
	public AcidicSprite() {
		super();
		
		texture( Assets.SCORPIO );
		
		TextureFilm frames = new TextureFilm( texture, 18, 17 );
		
		idle = new Animation( 12, true );
		idle.frames( frames, 14, 14, 14, 14, 14, 14, 14, 14, 15, 16, 15, 16, 15, 16 );
		
		run = new Animation( 4, true );
		run.frames( frames, 19, 20 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 14, 17, 18 );
		
		zap = attack.clone();
		
		die = new Animation( 12, false );
		die.frames( frames, 14, 21, 22, 23, 24 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xFF66FF22;
	}
}
