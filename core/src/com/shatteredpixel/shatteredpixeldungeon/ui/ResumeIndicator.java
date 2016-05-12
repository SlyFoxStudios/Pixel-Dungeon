
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.watabou.noosa.Image;

public class ResumeIndicator extends Tag {

	private Image icon;

	public ResumeIndicator() {
		super(0xCDD5C0);
		hotKey = GameAction.RESUME;

		setSize( 24, 24 );

		visible = false;

	}

	@Override
	protected void createChildren() {
		super.createChildren();

		icon = Icons.get( Icons.RESUME );
		add( icon );
	}

	@Override
	protected void layout() {
		super.layout();

		icon.x = x+1 + (width - icon.width) / 2f;
		icon.y = y + (height - icon.height) / 2f;
		PixelScene.align(icon);
	}

	@Override
	protected void onClick() {
		Dungeon.hero.resume();
	}

	@Override
	public void update() {
		if (!Dungeon.hero.isAlive())
			visible = false;
		else if (visible != (Dungeon.hero.lastAction != null)){
			visible = Dungeon.hero.lastAction != null;
			if (visible)
				flash();
		}
		super.update();
	}
}
