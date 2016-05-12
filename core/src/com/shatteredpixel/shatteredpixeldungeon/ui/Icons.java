
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.watabou.noosa.Image;

public enum Icons {

	SKULL,
	BUSY,
	COMPASS,
	INFO,
	PREFS,
	WARNING,
	TARGET,
	MASTERY,
	WATA,
	SHPX,
	WARRIOR,
	MAGE,
	ROGUE,
	HUNTRESS,
	CLOSE,
	DEPTH,
	DEPTH_LG,
	SLEEP,
	ALERT,
	BACKPACK,
	SEED_POUCH,
	SCROLL_HOLDER,
	POTION_BANDOLIER,
	WAND_HOLSTER,
	CHECKED,
	UNCHECKED,
	EXIT,
	NOTES,
	LANGS,
	CHALLENGE_OFF,
	CHALLENGE_ON,
	RESUME;

	public Image get() {
		return get( this );
	}
	
	public static Image get( Icons type ) {
		Image icon = new Image( Assets.ICONS );
		switch (type) {
		case SKULL:
			icon.frame( icon.texture.uvRect( 0, 0, 8, 8 ) );
			break;
		case BUSY:
			icon.frame( icon.texture.uvRect( 8, 0, 16, 8 ) );
			break;
		case COMPASS:
			icon.frame( icon.texture.uvRect( 0, 8, 7, 13 ) );
			break;
		case INFO:
			icon.frame( icon.texture.uvRect( 16, 0, 30, 14 ) );
			break;
		case PREFS:
			icon.frame( icon.texture.uvRect( 30, 0, 46, 16 ) );
			break;
		case WARNING:
			icon.frame( icon.texture.uvRect( 46, 0, 58, 12 ) );
			break;
		case TARGET:
			icon.frame( icon.texture.uvRect( 0, 13, 16, 29 ) );
			break;
		case MASTERY:
			icon.frame( icon.texture.uvRect( 16, 14, 30, 28 ) );
			break;
		case WATA:
			icon.frame( icon.texture.uvRect( 30, 16, 45, 26 ) );
			break;
		case SHPX:
			icon.frame( icon.texture.uvRect( 64, 44, 80, 60 ) );
			break;
		case WARRIOR:
			icon.frame( icon.texture.uvRect( 0, 29, 16, 45 ) );
			break;
		case MAGE:
			icon.frame( icon.texture.uvRect( 16, 29, 32, 45 ) );
			break;
		case ROGUE:
			icon.frame( icon.texture.uvRect( 32, 29, 48, 45 ) );
			break;
		case HUNTRESS:
			icon.frame( icon.texture.uvRect( 48, 29, 64, 45 ) );
			break;
		case CLOSE:
			icon.frame( icon.texture.uvRect( 0, 45, 13, 58 ) );
			break;
		case DEPTH:
			icon.frame( icon.texture.uvRect( 45, 12, 54, 20 ) );
			break;
		case DEPTH_LG:
			icon.frame( icon.texture.uvRect( 34, 46, 50, 62 ) );
			break;
		case SLEEP:
			icon.frame( icon.texture.uvRect( 13, 45, 22, 53 ) );
			break;
		case ALERT:
			icon.frame( icon.texture.uvRect( 22, 45, 30, 53 ) );
			break;
		case BACKPACK:
			icon.frame( icon.texture.uvRect( 58, 0, 68, 10 ) );
			break;
		case SCROLL_HOLDER:
			icon.frame( icon.texture.uvRect( 68, 0, 78, 10 ) );
			break;
		case SEED_POUCH:
			icon.frame( icon.texture.uvRect( 78, 0, 88, 10 ) );
			break;
		case WAND_HOLSTER:
			icon.frame( icon.texture.uvRect( 88, 0, 98, 10 ) );
			break;
		case POTION_BANDOLIER:
			icon.frame( icon.texture.uvRect( 98, 0, 108, 10 ) );
			break;
		case CHECKED:
			icon.frame( icon.texture.uvRect( 54, 12, 66, 24 ) );
			break;
		case UNCHECKED:
			icon.frame( icon.texture.uvRect( 66, 12, 78, 24 ) );
			break;
		case EXIT:
			icon.frame( icon.texture.uvRect( 108, 0, 124, 16 ) );
			break;
		case NOTES:
			icon.frame( icon.texture.uvRect( 79, 40, 94, 56 ) );
			break;
		case LANGS:
			icon.frame( icon.texture.uvRect( 94, 40, 110, 56 ) );
			break;
		case CHALLENGE_OFF:
			icon.frame( icon.texture.uvRect( 78, 16, 102, 40 ) );
			break;
		case CHALLENGE_ON:
			icon.frame( icon.texture.uvRect( 102, 16, 126, 40 ) );
			break;
		case RESUME:
			icon.frame( icon.texture.uvRect( 13, 53, 24, 64 ) );
			break;
		}
		return icon;
	}
	
	public static Image get( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return get( WARRIOR );
		case MAGE:
			return get( MAGE );
		case ROGUE:
			return get( ROGUE );
		case HUNTRESS:
			return get( HUNTRESS );
		default:
			return null;
		}
	}
}
