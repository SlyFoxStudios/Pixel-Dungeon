
package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TrapSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public abstract class Trap implements Bundlable {

	public String name = Messages.get(this, "name");

	public int color;
	public int shape;

	public int pos;

	public TrapSprite sprite;
	public boolean visible;
	public boolean active = true;

	public Trap set(int pos){
		this.pos = pos;
		return this;
	}

	public Trap reveal() {
		visible = true;
		if (sprite != null && sprite.visible == false) {
			sprite.visible = true;
			sprite.alpha( 0 );
			sprite.parent.add( new AlphaTweener( sprite, 1, 0.6f));
		}
		return this;
	}

	public Trap hide() {
		visible = false;
		if (sprite != null)
			sprite.visible = false;
		return this;
	}

	public void trigger() {
		if (active) {
			if (Dungeon.visible[pos]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			disarm();
			reveal();
			activate();
		}
	}

	public abstract void activate();

	protected void disarm(){
		Dungeon.level.disarmTrap(pos);
		active = false;
		if (sprite != null) {
			sprite.reset( this );
		}
	}

	private static final String POS	= "pos";
	private static final String VISIBLE	= "visible";
	private static final String ACTIVE = "active";

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		pos = bundle.getInt( POS );
		visible = bundle.getBoolean( VISIBLE );
		if (bundle.contains(ACTIVE)){
			active = bundle.getBoolean(ACTIVE);
		}
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		bundle.put( POS, pos );
		bundle.put( VISIBLE, visible );
		bundle.put( ACTIVE, active );
	}

	public String desc() {
		return Messages.get(this, "desc");
	}
}
