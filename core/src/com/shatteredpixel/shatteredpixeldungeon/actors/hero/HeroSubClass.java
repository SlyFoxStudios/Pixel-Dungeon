
package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

public enum HeroSubClass {

	NONE( null ),
	
	GLADIATOR( "gladiator" ),
	BERSERKER( "berserker" ),
	
	WARLOCK( "warlock" ),
	BATTLEMAGE( "battlemage" ),
	
	ASSASSIN( "assassin" ),
	FREERUNNER( "freerunner" ),
		
	SNIPER( "sniper" ),
	WARDEN( "warden" );
	
	private String title;
	
	HeroSubClass( String title ) {
		this.title = title;
	}
	
	public String title() {
		return Messages.get(this, title);
	}
	
	public String desc() {
		return Messages.get(this, title+"_desc");
	}
	
	private static final String SUBCLASS	= "subClass";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( SUBCLASS, toString() );
	}
	
	public static HeroSubClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( SUBCLASS );
		return valueOf( value );
	}
	
}
