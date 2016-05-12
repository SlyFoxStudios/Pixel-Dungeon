
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class ScrollPane extends Component {

	protected static final int THUMB_COLOR		= 0xFF7b8073;
	protected static final float THUMB_ALPHA	= 0.5f;

	protected TouchController controller;
	protected Component content;
	protected ColorBlock thumb;

	protected float minX;
	protected float minY;
	protected float maxX;
	protected float maxY;

	public ScrollPane( Component content ) {
		super();

		this.content = content;
		addToBack( content );

		width = content.width();
		height = content.height();

		content.camera = new Camera( 0, 0, 1, 1, PixelScene.defaultZoom );
		Camera.add( content.camera );
	}

	@Override
	public void destroy() {
		super.destroy();
		Camera.remove( content.camera );
	}

	public void scrollTo( float x, float y ) {
		content.camera.scroll.set( x, y );
	}

	@Override
	protected void createChildren() {
		controller = new TouchController();
		add( controller );

		thumb = new ColorBlock( 1, 1, THUMB_COLOR );
		thumb.am = THUMB_ALPHA;
		add( thumb );
	}

	@Override
	protected void layout() {

		content.setPos( 0, 0 );
		controller.x = x;
		controller.y = y;
		controller.width = width;
		controller.height = height;

		Point p = camera().cameraToScreen( x, y );
		Camera cs = content.camera;
		cs.x = p.x;
		cs.y = p.y;
		cs.resize( (int)width, (int)height );

		thumb.visible = height < content.height();
		if (thumb.visible) {
			thumb.scale.set( 2, height * height / content.height() );
			thumb.x = right() - thumb.width();
			thumb.y = y;
		}
	}

	public Component content() {
		return content;
	}

	public void onClick( float x, float y ) {
	}

	public class TouchController extends TouchArea {

		private float dragThreshold;

		public TouchController() {
			super( 0, 0, 0, 0 );
			dragThreshold = PixelScene.defaultZoom * 8;
		}

		@Override
		protected void onTouchUp( NoosaInputProcessor.Touch touch ) {
			if (dragging) {

				dragging = false;
				thumb.am = THUMB_ALPHA;

			} else {

				PointF p = content.camera.screenToCamera( (int)touch.current.x, (int)touch.current.y );
				ScrollPane.this.onClick( p.x, p.y );

			}
		}

		private boolean dragging = false;
		private PointF lastPos = new PointF();

		@Override
		protected void onDrag( NoosaInputProcessor.Touch t ) {
			if (dragging) {

				doScroll(t.current);

			} else if (PointF.distance( t.current, t.start ) > dragThreshold) {

				dragging = true;
				lastPos.set( t.current );
				thumb.am = 1;

			}
		}

		@Override
		public boolean onMouseScroll(int scroll) {
			PointF newPt = new PointF(lastPos);
			newPt.y -= scroll * content.camera.zoom * 4;
			doScroll(newPt);
			return true;
		}

		private void doScroll(PointF current) {
			final Camera c = content.camera;

			c.scroll.offset( PointF.diff(lastPos, current).invScale( c.zoom ) );
			if (c.scroll.x + width > content.width()) {
				c.scroll.x = content.width() - width;
			}
			if (c.scroll.x < 0) {
				c.scroll.x = 0;
			}
			if (c.scroll.y + height > content.height()) {
				c.scroll.y = content.height() - height;
			}
			if (c.scroll.y < 0) {
				c.scroll.y = 0;
			}

			thumb.y = y + height * c.scroll.y / content.height();

			lastPos.set(current);
		}
	}
}
