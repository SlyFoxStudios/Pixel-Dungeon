/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.BannerSprites;
import com.shatteredpixel.shatteredpixeldungeon.effects.Fireball;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

public class WelcomeScene extends PixelScene {

	private static int LATEST_UPDATE = 107;

	@Override
	public void create() {
		super.create();

		final int previousVersion = ShatteredPixelDungeon.version();

		if (ShatteredPixelDungeon.versionCode == previousVersion) {
			ShatteredPixelDungeon.switchNoFade(TitleScene.class);
			return;
		}

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		title.brightness(0.6f);
		add( title );

		float height = title.height +
				(ShatteredPixelDungeon.landscape() ? 48 : 96);

		title.x = (w - title.width()) / 2;
		title.y = (h - height) / 2;

		placeTorch(title.x + 18, title.y + 20);
		placeTorch(title.x + title.width - 18, title.y + 20);

		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = (float)Math.sin( -(time += Game.elapsed) );
			}
			@Override
			public void draw() {
				Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE );
				super.draw();
				Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			}
		};
		signs.x = title.x;
		signs.y = title.y;
		add( signs );

		DarkRedButton okay = new DarkRedButton(Messages.get(this, "continue")){
			@Override
			protected void onClick() {
				super.onClick();
				updateVersion(previousVersion);
				ShatteredPixelDungeon.switchScene(TitleScene.class);
			}
		};

		if (previousVersion != 0){
			DarkRedButton changes = new DarkRedButton(Messages.get(this, "changelist")){
				@Override
				protected void onClick() {
					super.onClick();
					updateVersion(previousVersion);
					ShatteredPixelDungeon.switchScene(ChangesScene.class);
				}
			};
			okay.setRect(title.x, h-20, (title.width()/2)-2, 16);
			okay.textColor(0xBBBB33);
			add(okay);

			changes.setRect(okay.right()+2, h-20, (title.width()/2)-2, 16);
			changes.textColor(0xBBBB33);
			add(changes);
		} else {
			okay.setRect(title.x, h-20, title.width(), 16);
			okay.textColor(0xBBBB33);
			add(okay);
		}

		RenderedTextMultiline text = PixelScene.renderMultiline(6);
		String message;
		if (previousVersion == 0) {
			message = Messages.get(this, "welcome_msg");
		} else if (previousVersion <= ShatteredPixelDungeon.versionCode) {
			if (previousVersion < LATEST_UPDATE){
				message = Messages.get(this, "update_intro");
				message += "\n\n" + Messages.get(this, "update_msg");
			} else {
				//TODO: change the messages here in accordance with the type of patch.
				message = Messages.get(this, "patch_intro");
				message += "\n\n" + Messages.get(this, "patch_bugfixes");
				message += "\n" + Messages.get(this, "patch_translations");
				message += "\n" + Messages.get(this, "patch_balance");

			}
		} else {
			message = Messages.get(this, "what_msg");
		}
		text.text(message, w-20);
		float textSpace = h - title.y - (title.height() - 10) - okay.height() - 2;
		text.setPos(10, title.y+(title.height() - 10) + ((textSpace - text.height()) / 2));
		add(text);

	}

	private void updateVersion(int previousVersion){
		ShatteredPixelDungeon.version(ShatteredPixelDungeon.versionCode);
	}

	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
	}

	private class DarkRedButton extends RedButton{
		{
			bg.brightness(0.4f);
		}

		DarkRedButton(String text){
			super(text);
		}

		@Override
		protected void onTouchDown() {
			bg.brightness(0.5f);
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}

		@Override
		protected void onTouchUp() {
			super.onTouchUp();
			bg.brightness(0.4f);
		}
	}
}
