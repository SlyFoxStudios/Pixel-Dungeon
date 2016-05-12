
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Component;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;

public class GoldIndicator extends Component {

	private static final float TIME	= 2f;
	
	private int lastValue = 0;
	
	private BitmapText tf;
	
	private float time;
	
	@Override
	protected void createChildren() {
		tf = new BitmapText( PixelScene.pixelFont);
		tf.hardlight( 0xFFFF00 );
		add( tf );
		
		visible = false;
	}
	
	@Override
	protected void layout() {
		tf.x = x + (width - tf.width()) / 2;
		tf.y = bottom() - tf.height();
	}
	
	@Override
	public void update() {
		super.update();
		
		if (visible) {
			
			time -= Game.elapsed;
			if (time > 0) {
				tf.alpha( time > TIME / 2 ? 1f : time * 2 / TIME );
			} else {
				visible = false;
			}
			
		}
		
		if (Dungeon.gold != lastValue) {
			
			lastValue = Dungeon.gold;
			
			tf.text( Integer.toString( lastValue ) );
			tf.measure();
			
			visible = true;
			time = TIME;
			
			layout();
		}
	}
}
