
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TouchArea;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.effects.ShadowBox;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.watabou.utils.Signal;

//TODO: need to do a big consistency pass on windows now that text size is consistent (larger in many cases)
public class Window extends Group implements Signal.Listener<NoosaInputProcessor.Key<GameAction>> {

	protected int width;
	protected int height;
	
	protected TouchArea blocker;
	protected ShadowBox shadow;
	protected NinePatch chrome;
	
	public static final int TITLE_COLOR = 0xFFFF44;
	public static final int SHPX_COLOR = 0x3333bb;
	
	public Window() {
		this( 0, 0, Chrome.get( Chrome.Type.WINDOW ) );
	}
	
	public Window( int width, int height ) {
		this( width, height, Chrome.get( Chrome.Type.WINDOW ) );
	}
			
	public Window( int width, int height, NinePatch chrome ) {
		super();
		
		blocker = new TouchArea( 0, 0, PixelScene.uiCamera.width, PixelScene.uiCamera.height ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				if (!Window.this.chrome.overlapsScreenPoint(
					(int)touch.current.x,
					(int)touch.current.y )) {
					
					onBackPressed();
				}
			}
		};
		blocker.camera = PixelScene.uiCamera;
		add( blocker );
		
		this.chrome = chrome;

		this.width = width;
		this.height = height;

		shadow = new ShadowBox();
		shadow.am = 0.5f;
		shadow.camera = PixelScene.uiCamera.visible ?
				PixelScene.uiCamera : Camera.main;
		add( shadow );

		chrome.x = -chrome.marginLeft();
		chrome.y = -chrome.marginTop();
		chrome.size(
			width - chrome.x + chrome.marginRight(),
			height - chrome.y + chrome.marginBottom() );
		add( chrome );
		
		camera = new Camera( 0, 0,
			(int)chrome.width,
			(int)chrome.height,
			PixelScene.defaultZoom );
		camera.x = (int)(Game.width - camera.width * camera.zoom) / 2;
		camera.y = (int)(Game.height - camera.height * camera.zoom) / 2;
		camera.scroll.set( chrome.x, chrome.y );
		Camera.add( camera );

		shadow.boxRect(
				camera.x / camera.zoom,
				camera.y / camera.zoom,
				chrome.width(), chrome.height );

		Game.instance.getInputProcessor().addKeyListener(this);
	}
	
	public void resize( int w, int h ) {
		this.width = w;
		this.height = h;

		chrome.size(
			width + chrome.marginHor(),
			height + chrome.marginVer() );
		
		camera.resize( (int)chrome.width, (int)chrome.height );
		camera.x = (int)(Game.width - camera.screenWidth()) / 2;
		camera.y = (int)(Game.height - camera.screenHeight()) / 2;

		shadow.boxRect( camera.x / camera.zoom, camera.y / camera.zoom, chrome.width(), chrome.height );
	}
	
	public void hide() {
		parent.erase( this );
		destroy();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		Camera.remove( camera );
		Game.instance.getInputProcessor().removeKeyListener(this);
	}

	@Override
	public void onSignal( NoosaInputProcessor.Key<GameAction> key ) {
		if (key.pressed) {
			switch (key.code) {
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
				onBackPressed();
				break;
			case Input.Keys.MENU:
				onMenuPressed();
				break;
			default:
				onKeyDown(key);
				break;
			}
		} else {
			onKeyUp( key );
		}

		Game.instance.getInputProcessor().cancelKeyEvent();
	}

	protected void onKeyDown(NoosaInputProcessor.Key key) {
	}

	protected void onKeyUp( NoosaInputProcessor.Key<GameAction> key ) {
	}

	public void onBackPressed() {
		hide();
	}
	
	public void onMenuPressed() {
	}
}
