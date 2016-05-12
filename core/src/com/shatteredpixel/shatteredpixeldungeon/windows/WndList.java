
package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.RenderedText;

public class WndList extends Window {
	
	private static final int WIDTH	= 120;
	private static final int MARGIN	= 4;
	private static final int GAP	= 4;
	
	public WndList( String[] items ) {
		
		super();
		
		float pos = MARGIN;
		float dotWidth = 0;
		float maxWidth = 0;
		
		for (int i=0; i < items.length; i++) {
			
			if (i > 0) {
				pos += GAP;
			}
			
			RenderedText dot = PixelScene.renderText( "-", 6 );
			dot.x = MARGIN;
			dot.y = pos;
			if (dotWidth == 0) {
				dotWidth = dot.width();
			}
			add( dot );
			
			BitmapTextMultiline item = PixelScene.createMultiline( items[i], 6 );
			item.x = dot.x + dotWidth;
			item.y = pos;
			item.maxWidth = (int)(WIDTH - MARGIN * 2 - dotWidth);
			item.measure();
			add( item );
			
			pos += item.height();
			float w = item.width();
			if (w > maxWidth) {
				maxWidth = w;
			}
		}

		resize( (int)(maxWidth + dotWidth + MARGIN * 2), (int)(pos + MARGIN) );
	}
}
