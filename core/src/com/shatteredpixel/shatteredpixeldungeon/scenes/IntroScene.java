
package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndStory;
import com.watabou.noosa.Game;

public class IntroScene extends PixelScene {

	@Override
	public void create() {
		super.create();

		add( new WndStory( Messages.get(this, "text") ) {
			@Override
			public void hide() {
				super.hide();
				Game.switchScene( InterlevelScene.class );
			}
		} );

		fadeIn();
	}
}
