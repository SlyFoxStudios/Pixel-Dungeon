
package com.shatteredpixel.shatteredpixeldungeon.ui;

		import com.watabou.noosa.Game;
		import com.watabou.noosa.Image;
		import com.watabou.noosa.audio.Sample;
		import com.watabou.noosa.ui.Button;
		import com.shatteredpixel.shatteredpixeldungeon.Assets;
		import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
		import com.shatteredpixel.shatteredpixeldungeon.scenes.TitleScene;

public class ExitButton extends Button {

	protected Image image;

	public ExitButton() {
		super();

		width = image.width;
		height = image.height;
	}

	@Override
	protected void createChildren() {
		super.createChildren();

		image = Icons.EXIT.get();
		add( image );
	}

	@Override
	protected void layout() {
		super.layout();

		image.x = x;
		image.y = y;
	}

	@Override
	protected void onTouchDown() {
		image.brightness( 1.5f );
		Sample.INSTANCE.play( Assets.SND_CLICK );
	}

	@Override
	protected void onTouchUp() {
		image.resetColor();
	}

	@Override
	protected void onClick() {
		if (Game.scene() instanceof TitleScene) {
			Game.instance.finish();
		} else {
			ShatteredPixelDungeon.switchNoFade( TitleScene.class );
		}
	}
}
