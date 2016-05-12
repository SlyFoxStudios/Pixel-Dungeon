
package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.badlogic.gdx.Gdx;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.Scheduler;
import java.awt.Color;
import com.shatteredpixel.shatteredpixeldungeon.utils.Rainbow;
import java.util.Random;

public class AboutScene extends PixelScene {

	private final Developer[] Developers = new Developer[] {
		new Developer("McSwaggens", "www.github.com/McSwaggens/", "Developer", Icons.WATA.get(), 0xBA53EC, true),
		new Developer("CrazyWolf", "www.github.com/CrazyWolf132/", "Developer", Icons.SHPX.get(), 0xF1F1F1, true)
	};
	
	private Scheduler scheduler;

	@Override
	public void create() {
		
		scheduler = new Scheduler(1) {
			public void Execute() {
				for (Developer developer : Developers) {
					if (developer.rainbow){
						developer.at += 3;
						if (developer.at >= Rainbow.rainbow.size())
							developer.at = 0;
						developer.flare.color(getIntFromColor(Rainbow.rainbow.get(developer.at)), true);
					}
				}
			}
			
			public int getIntFromColor(Color color){
				int Red = color.getRed();
				int Green = color.getGreen();
				int Blue = color.getBlue();
				
				int rgb = Red;
				rgb = (rgb << 8) + Green;
				rgb = (rgb << 8) + Blue;
				return rgb;
			}
		};
		
		scheduler.StartLoop();
		
		super.create();
		final float colTop = (Camera.main.height / 2) - (ShatteredPixelDungeon.landscape() ? 30 : 90);
		final float colWidth = (Camera.main.width / Developers.length);
		for (int i = 0; i < Developers.length; i++){
			
			final Developer developer = Developers[i];
			
			Image icon = developer.icon;
			icon.x = ((i * colWidth) + (colWidth) / 2) - (icon.width() / 2);
			icon.y = colTop;
			align(icon);
			add( icon );

			developer.flare = new Flare( 7, 64 ).color( developer.rayColor, true ).show( icon, 0 );
			developer.flare.angularSpeed = +60;

			RenderedText title = renderText( developer.name, 8 );
			title.hardlight( Window.SHPX_COLOR );
			add( title );

			title.x = ((i * colWidth) + (colWidth) / 2) - (title.width() / 2);
			title.y = icon.y + icon.height + 5;
			align(title);
			
			
		}
		
		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}
	
	@Override
	public void update() {
		super.update();
		
	}
	
	@Override
	public void destroy() {
		super.destroy();
		scheduler.destroy();
	}
	
	public class Developer {
		public String name;
		public String website;
		public String roles;
		public Image icon;
		public int rayColor;
		public boolean rainbow = false;
		public Flare flare;
		public int at = new Random().nextInt(600);
		
		public Developer(String name, String website, String roles, Image icon, int rayColor, boolean rainbow) {
			this.name = name;
			this.website = website;
			this.roles = roles;
			this.icon = icon;
			this.rayColor = rayColor;
			this.rainbow = rainbow;
		}
	}
}
