
package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.watabou.noosa.Game;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Halo;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.watabou.utils.PointF;

public class WandmakerSprite extends MobSprite {
	
	private Shield shield;
	
	public WandmakerSprite() {
		super();
		
		texture( Assets.MAKER );
		
		TextureFilm frames = new TextureFilm( texture, 12, 14 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 3, 2, 1 );
		
		run = new Animation( 20, true );
		run.frames( frames, 0 );
		
		die = new Animation( 20, false );
		die.frames( frames, 0 );
		
		play( idle );
	}
	
	@Override
	public void link( Char ch ) {
		super.link( ch );
		
		if (shield == null) {
			parent.add( shield = new Shield() );
		}
	}
	
	@Override
	public void die() {
		super.die();
		
		if (shield != null) {
			shield.putOut();
		}
		emitter().start( ElmoParticle.FACTORY, 0.03f, 60 );

		if (visible) {
			Sample.INSTANCE.play( Assets.SND_BURNING );
		}
	}
	
	public class Shield extends Halo {
		
		private float phase;
		
		public Shield() {
			
			super( 14, 0xBBAACC, 1f );
			
			am = -1;
			aa = +1;
			
			phase = 1;
		}
		
		@Override
		public void update() {
			super.update();
			
			if (phase < 1) {
				if ((phase -= Game.elapsed) <= 0) {
					killAndErase();
				} else {
					scale.set( (2 - phase) * radius / RADIUS );
					am = phase * (-1);
					aa = phase * (+1);
				}
			}
			
			if (visible = WandmakerSprite.this.visible) {
				PointF p = WandmakerSprite.this.center();
				point(p.x, p.y );
			}
		}
		
		@Override
		public void draw() {
			Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE );
			super.draw();
			Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
		}
		
		public void putOut() {
			phase = 0.999f;
		}
	}

}
