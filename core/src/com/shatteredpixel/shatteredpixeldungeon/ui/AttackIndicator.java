
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Game;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AttackIndicator extends Tag {
	
	private static final float ENABLED	= 1.0f;
	private static final float DISABLED	= 0.3f;
	
	private static float delay;
	
	private static AttackIndicator instance;
	
	private CharSprite sprite = null;
	
	private static Mob lastTarget;
	private ArrayList<Mob> candidates = new ArrayList<Mob>();

	public AttackIndicator() {
		super( DangerIndicator.COLOR );
		
		instance = this;
		lastTarget = null;

		hotKey = GameAction.TAG_ATTACK;
		
		setSize( 24, 24 );
		visible( false );
		enable( false );
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
	}
	
	@Override
	protected void layout() {
		super.layout();
		
		if (sprite != null) {
			sprite.x = x + (width - sprite.width()) / 2;
			sprite.y = y + (height - sprite.height()) / 2;
			PixelScene.align(sprite);
		}
	}
	
	@Override
	public void update() {
		super.update();
		
		if (!bg.visible){
			enable(false);
			if (delay > 0f) delay -= Game.elapsed;
			if (delay <= 0f) active = false;
		} else {
			delay = 0.75f;
			active = true;
		
			if (Dungeon.hero.isAlive()) {

				enable(Dungeon.hero.ready);

			} else {
				visible( false );
				enable( false );
			}
		}
	}
	
	private void checkEnemies() {

		candidates.clear();
		int v = Dungeon.hero.visibleEnemies();
		for (int i=0; i < v; i++) {
			Mob mob = Dungeon.hero.visibleEnemy( i );
			if ( Dungeon.hero.canAttack( mob) ) {
				candidates.add( mob );
			}
		}
		
		if (!candidates.contains( lastTarget )) {
			if (candidates.isEmpty()) {
				lastTarget = null;
			} else {
				active = true;
				lastTarget = Random.element( candidates );
				updateImage();
				flash();
			}
		} else {
			if (!bg.visible) {
				active = true;
				flash();
			}
		}
		
		visible( lastTarget != null );
		enable( bg.visible );
	}
	
	private void updateImage() {
		
		if (sprite != null) {
			sprite.killAndErase();
			sprite = null;
		}
		
		try {
			sprite = lastTarget.spriteClass.newInstance();
			active = true;
			sprite = ClassReflection.newInstance(lastTarget.spriteClass);
			sprite.idle();
			sprite.paused = true;
			add( sprite );

			sprite.x = x + (width - sprite.width()) / 2 + 1;
			sprite.y = y + (height - sprite.height()) / 2;
			PixelScene.align(sprite);
			
		} catch (Exception ignored) {
		}
	}
	
	private boolean enabled = true;
	private void enable( boolean value ) {
		enabled = value;
		if (sprite != null) {
			sprite.alpha( value ? ENABLED : DISABLED );
		}
	}
	
	private void visible( boolean value ) {
		bg.visible = value;
		if (sprite != null) {
			sprite.visible = value;
		}
	}
	
	@Override
	protected void onClick() {
		if (enabled) {
			Dungeon.hero.handle( lastTarget.pos );
		}
	}
	
	public static void target( Char target ) {
		lastTarget = (Mob)target;
		instance.updateImage();
		
		HealthIndicator.instance.target( target );
	}
	
	public static void updateState() {
		instance.checkEnemies();
	}
}
