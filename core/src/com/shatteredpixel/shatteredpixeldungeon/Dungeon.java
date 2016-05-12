package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.levels.CavesBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.CavesLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.CityBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.CityLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.DeadEndLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.HallsBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.HallsLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.LastLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.LastShopLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.PrisonBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.PrisonLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.StartScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndResurrect;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Dungeon {

	public static int transmutation;	// depth number for a well of transmutation

	//enum of items which have limited spawns, records how many have spawned
	//could all be their own separate numbers, but this allows iterating, much nicer for bundling/initializing.
	//TODO: this is fairly brittle when it comes to bundling, should look into a more flexible solution.
	public static enum limitedDrops{
		//limited world drops
		strengthPotions,
		upgradeScrolls,
		arcaneStyli,

		//all unlimited health potion sources (except guards, which are at the bottom.
		swarmHP,
		batHP,
		warlockHP,
		scorpioHP,
		cookingHP,
		//blandfruit, which can technically be an unlimited health potion source
		blandfruitSeed,

		//doesn't use Generator, so we have to enforce one armband drop here
		armband,

		//containers
		dewVial,
		seedBag,
		scrollBag,
		potionBag,
		wandBag,

		guardHP;

		public int count = 0;

		//for items which can only be dropped once, should directly access count otherwise.
		public boolean dropped(){
			return count != 0;
		}
		public void drop(){
			count = 1;
		}
	}

	public static int challenges;

	public static Hero hero;
	public static Level level;

	public static QuickSlot quickslot = new QuickSlot();

	public static int depth;
	public static int gold;

	public static HashSet<Integer> chapters;

	// Hero's field of view
	public static boolean[] visible = new boolean[Level.LENGTH];

	public static SparseArray<ArrayList<Item>> droppedItems;

	public static int version;

	public static void init() {

		version = Game.versionCode;
		challenges = ShatteredPixelDungeon.challenges();

		Actor.clear();
		Actor.resetNextID();

		PathFinder.setMapSize( Level.WIDTH, Level.HEIGHT );

		Scroll.initLabels();
		Potion.initColors();
		Ring.initGems();

		Statistics.reset();
		Journal.reset();

		quickslot.reset();
		QuickSlotButton.reset();

		depth = 0;
		gold = 0;

		droppedItems = new SparseArray<ArrayList<Item>>();

		for (limitedDrops a : limitedDrops.values())
			a.count = 0;

		transmutation = Random.IntRange( 6, 14 );

		chapters = new HashSet<Integer>();

		Ghost.Quest.reset();
		Wandmaker.Quest.reset();
		Blacksmith.Quest.reset();
		Imp.Quest.reset();

		Room.shuffleTypes();

		Generator.initArtifacts();
		hero = new Hero();
		hero.live();

		Badges.reset();

		StartScene.curClass.initHero( hero );
	}

	public static boolean isChallenged( int mask ) {
		return (challenges & mask) != 0;
	}

	public static Level newLevel() {

		Dungeon.level = null;
		Actor.clear();

		depth++;
		if (depth > Statistics.deepestFloor) {
			Statistics.deepestFloor = depth;

			if (Statistics.qualifiedForNoKilling) {
				Statistics.completedWithNoKilling = true;
			} else {
				Statistics.completedWithNoKilling = false;
			}
		}

		Arrays.fill( visible, false );

		Level level;
		switch (depth) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				level = new SewerLevel();
				break;
			case 6:
				level = new SewerBossLevel();
				break;
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				level = new PrisonLevel();
				break;
			case 12:
				level = new PrisonBossLevel();
				break;
			case 13:
			case 14:
			case 15:
			case 16:
				level = new CavesLevel();
				break;
			case 17:
				level = new CavesLevel(); // add in special Dwarven Tinkerer level here...
				break;
			case 18:
				level = new CavesBossLevel();
				break;
			case 19:
			case 20:
			case 21:
			case 22:
				level = new CityLevel();
				break;
			case 23:
				level = new CityLevel(); // add in special Minotaur - MAZE level here...
				break;
			case 24:
				level = new CityBossLevel();
				break;
			case 25:
				level = new LastShopLevel();
				break;
			case 26:
			case 27:
			case 28:
				level = new HallsLevel();
				break;
			case 29:
				level = new HallsLevel(); // add in special Chaos Mage level here...
				break;
			case 30:
				level = new HallsBossLevel();
				break;
			case Level.MAX_DEPTH:
				level = new LastLevel();
				break;
			default:
				level = new DeadEndLevel();
				Statistics.deepestFloor--;
		}

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static void resetLevel() {

		Actor.clear();

		Arrays.fill( visible, false );

		level.reset();
		switchLevel( level, level.entrance );
	}

	public static boolean shopOnLevel() {
		return depth == 7 || depth == 13 || depth == 19;
	}

	public static boolean bossLevel() {
		return bossLevel( depth );
	}

	public static boolean bossLevel( int depth ) {
		return depth == 6 || depth == 12 || depth == 18 || depth == 24 || depth == 30;
	}

	@SuppressWarnings("deprecation")
	public static void switchLevel( final Level level, int pos ) {

		Dungeon.level = level;
		Actor.init();

		Actor respawner = level.respawner();
		if (respawner != null) {
			Actor.add( level.respawner() );
		}

		hero.pos = pos != -1 ? pos : level.exit;

		Light light = hero.buff( Light.class );
		hero.viewDistance = light == null ? level.viewDistance : Math.max( Light.DISTANCE, level.viewDistance );

		//logic for pre 0.3.0 saves, need to give mages a staff.
		if (Dungeon.version <= 38 && Dungeon.hero.heroClass == HeroClass.MAGE){
			MagesStaff staff = new MagesStaff();
			staff.identify();
			if (!staff.collect(Dungeon.hero.belongings.backpack)){
				Dungeon.level.drop(staff, Dungeon.hero.pos);
			}
		}

		observe();
		try {
			saveAll();
		} catch (IOException e) {
			/*This only catches IO errors. Yes, this means things can go wrong, and they can go wrong catastrophically.
			But when they do the user will get a nice 'report this issue' dialogue, and I can fix the bug.*/
		}
	}

	public static void dropToChasm( Item item ) {
		int depth = Dungeon.depth + 1;
		ArrayList<Item> dropped = (ArrayList<Item>)Dungeon.droppedItems.get( depth );
		if (dropped == null) {
			Dungeon.droppedItems.put( depth, dropped = new ArrayList<Item>() );
		}
		dropped.add( item );
	}

	public static boolean posNeeded() {
		// adjusted slightly to account for larger dungeon size... still caps out at 9 upgrades by the end
		int[] quota = {5, 2,   11, 4,   17, 6,   23, 8,    29, 9};
		return chance( quota, limitedDrops.strengthPotions.count );
	}

	public static boolean souNeeded() {
		// adjusted slightly to account for larger dungeon, level caps and upgrade failures
		int[] quota = {6, 4,   12, 8,   18, 13,   24, 16,   29, 18};
		return chance( quota, limitedDrops.upgradeScrolls.count );
	}

	private static boolean chance( int[] quota, int number ) {

		for (int i=0; i < quota.length; i += 2) {
			int qDepth = quota[i];
			if (depth <= qDepth) {
				int qNumber = quota[i + 1];
				return Random.Float() < (float)(qNumber - number) / (qDepth - depth + 1);
			}
		}

		return false;
	}
	// guaranteed to drop at least one Arcane Styluses within your game
	public static boolean asNeeded() {
		return Random.Int( 12 * (1 + limitedDrops.arcaneStyli.count) ) < depth;
	}

	private static final String RG_GAME_FILE	= "game.dat";
	private static final String RG_DEPTH_FILE	= "depth%d.dat";

	private static final String WR_GAME_FILE	= "warrior.dat";
	private static final String WR_DEPTH_FILE	= "warrior%d.dat";

	private static final String MG_GAME_FILE	= "mage.dat";
	private static final String MG_DEPTH_FILE	= "mage%d.dat";

	private static final String RN_GAME_FILE	= "ranger.dat";
	private static final String RN_DEPTH_FILE	= "ranger%d.dat";

	private static final String VERSION		= "version";
	private static final String CHALLENGES	= "challenges";
	private static final String HERO		= "hero";
	private static final String GOLD		= "gold";
	private static final String DEPTH		= "depth";
	private static final String DROPPED     = "dropped%d";
	private static final String LEVEL		= "level";
	private static final String LIMDROPS    = "limiteddrops";
	private static final String DV			= "dewVial";
	private static final String WT			= "transmutation";
	private static final String CHAPTERS	= "chapters";
	private static final String QUESTS		= "quests";
	private static final String BADGES		= "badges";

	//TODO: to support pre-0.2.3 saves, remove when needed
	private static final String POS			= "potionsOfStrength";
	private static final String SOU			= "scrollsOfEnhancement";
	private static final String AS			= "arcaneStyli";

	public static String gameFile( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return WR_GAME_FILE;
		case MAGE:
			return MG_GAME_FILE;
		case HUNTRESS:
			return RN_GAME_FILE;
		default:
			return RG_GAME_FILE;
		}
	}

	private static String depthFile( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return WR_DEPTH_FILE;
		case MAGE:
			return MG_DEPTH_FILE;
		case HUNTRESS:
			return RN_DEPTH_FILE;
		default:
			return RG_DEPTH_FILE;
		}
	}

	public static void saveGame( String fileName ) throws IOException {
		try {
			Bundle bundle = new Bundle();

			version = Game.versionCode;
			bundle.put( VERSION, version );
			bundle.put( CHALLENGES, challenges );
			bundle.put( HERO, hero );
			bundle.put( GOLD, gold );
			bundle.put( DEPTH, depth );

			for (int d : droppedItems.keyArray()) {
				bundle.put(String.format(DROPPED, d), droppedItems.get(d));
			}

			quickslot.storePlaceholders( bundle );

			bundle.put( WT, transmutation );

			int[] dropValues = new int[limitedDrops.values().length];
			for (limitedDrops value : limitedDrops.values())
				dropValues[value.ordinal()] = value.count;
			bundle.put ( LIMDROPS, dropValues );

			int count = 0;
			int ids[] = new int[chapters.size()];
			for (Integer id : chapters) {
				ids[count++] = id;
			}
			bundle.put( CHAPTERS, ids );

			Bundle quests = new Bundle();
			Ghost		.Quest.storeInBundle( quests );
			Wandmaker	.Quest.storeInBundle( quests );
			Blacksmith	.Quest.storeInBundle( quests );
			Imp			.Quest.storeInBundle( quests );
			bundle.put( QUESTS, quests );

			Room.storeRoomsInBundle( bundle );

			Statistics.storeInBundle( bundle );
			Journal.storeInBundle( bundle );
			Generator.storeInBundle( bundle );

			Scroll.save( bundle );
			Potion.save( bundle );
			Ring.save( bundle );

			Actor.storeNextID( bundle );

			Bundle badges = new Bundle();
			Badges.saveLocal( badges );
			bundle.put( BADGES, badges );

			OutputStream output = Game.instance.openFileOutput( fileName );
			Bundle.write( bundle, output );
			output.close();

		} catch (IOException e) {

			GamesInProgress.setUnknown( hero.heroClass );
		}
	}

	public static void saveLevel() throws IOException {
		Bundle bundle = new Bundle();
		bundle.put( LEVEL, level );

		OutputStream output = Game.instance.openFileOutput( Messages.format( depthFile( hero.heroClass ), depth ) );
		Bundle.write( bundle, output );
		output.close();
	}

	public static void saveAll() throws IOException {
		if (hero.isAlive()) {

			Actor.fixTime();
			saveGame( gameFile( hero.heroClass ) );
			saveLevel();

			GamesInProgress.set( hero.heroClass, depth, hero.lvl, challenges != 0 );

		} else if (WndResurrect.instance != null) {

			WndResurrect.instance.hide();
			Hero.reallyDie( WndResurrect.causeOfDeath );

		}
	}

	public static void loadGame( HeroClass cl ) throws IOException {
		loadGame( gameFile( cl ), true );
	}

	public static void loadGame( String fileName ) throws IOException {
		loadGame( fileName, false );
	}

	public static void loadGame( String fileName, boolean fullLoad ) throws IOException {

		Bundle bundle = gameBundle( fileName );

		version = bundle.getInt( VERSION );

		Generator.reset();

		Actor.restoreNextID( bundle );

		quickslot.reset();
		QuickSlotButton.reset();

		Dungeon.challenges = bundle.getInt( CHALLENGES );

		Dungeon.level = null;
		Dungeon.depth = -1;

		if (fullLoad) {
			PathFinder.setMapSize( Level.WIDTH, Level.HEIGHT );
		}

		Scroll.restore( bundle );
		Potion.restore( bundle );
		Ring.restore( bundle );

		quickslot.restorePlaceholders( bundle );

		if (fullLoad) {
			transmutation = bundle.getInt( WT );

			//TODO: adjust this when dropping support for pre-0.2.3 saves
			if (bundle.contains( LIMDROPS )) {
				int[] dropValues = bundle.getIntArray(LIMDROPS);
				for (limitedDrops value : limitedDrops.values())
					value.count = value.ordinal() < dropValues.length ?
							dropValues[value.ordinal()] : 0;
			} else {
				for (limitedDrops value : limitedDrops.values())
					value.count = 0;
				limitedDrops.strengthPotions.count = bundle.getInt(POS);
				limitedDrops.upgradeScrolls.count = bundle.getInt(SOU);
				limitedDrops.arcaneStyli.count = bundle.getInt(AS);
			}
			//for pre-0.2.4 saves
			if (bundle.getBoolean(DV)) limitedDrops.dewVial.drop();

			chapters = new HashSet<Integer>();
			int ids[] = bundle.getIntArray( CHAPTERS );
			if (ids != null) {
				for (int id : ids) {
					chapters.add( id );
				}
			}

			Bundle quests = bundle.getBundle( QUESTS );
			if (!quests.isNull()) {
				Ghost.Quest.restoreFromBundle( quests );
				Wandmaker.Quest.restoreFromBundle( quests );
				Blacksmith.Quest.restoreFromBundle( quests );
				Imp.Quest.restoreFromBundle( quests );
			} else {
				Ghost.Quest.reset();
				Wandmaker.Quest.reset();
				Blacksmith.Quest.reset();
				Imp.Quest.reset();
			}

			Room.restoreRoomsFromBundle(bundle);
		}

		Bundle badges = bundle.getBundle(BADGES);
		if (!badges.isNull()) {
			Badges.loadLocal( badges );
		} else {
			Badges.reset();
		}

		hero = null;
		hero = (Hero)bundle.get( HERO );

		gold = bundle.getInt( GOLD );
		depth = bundle.getInt( DEPTH );

		Statistics.restoreFromBundle( bundle );
		Journal.restoreFromBundle( bundle );
		Generator.restoreFromBundle( bundle );

		droppedItems = new SparseArray<ArrayList<Item>>();
		for (int i=2; i <= Statistics.deepestFloor + 1; i++) {
			ArrayList<Item> dropped = new ArrayList<Item>();
			for (Bundlable b : bundle.getCollection( String.format( DROPPED, i ) ) ) {
				dropped.add( (Item)b );
			}
			if (!dropped.isEmpty()) {
				droppedItems.put( i, dropped );
			}
		}

		//logic for pre 0.2.4 bags, remove when no longer supporting those saves.
		if (version <= 32){
			int deepest = Statistics.deepestFloor;
			if (deepest > 15) limitedDrops.wandBag.count = 1;
			if (deepest > 10) limitedDrops.scrollBag.count = 1;
			if (deepest > 5)  limitedDrops.seedBag.count = 1;
		}
	}

	public static Level loadLevel( HeroClass cl ) throws IOException {

		Dungeon.level = null;
		Actor.clear();

		InputStream input = Game.instance.openFileInput( Messages.format( depthFile( cl ), depth ) ) ;
		Bundle bundle = Bundle.read( input );
		input.close();

		return (Level)bundle.get( "level" );
	}

	public static void deleteGame( HeroClass cl, boolean deleteLevels ) {

		Game.instance.deleteFile( gameFile( cl ) );

		if (deleteLevels) {
			int depth = 1;
			while (Game.instance.deleteFile( Messages.format( depthFile( cl ), depth ) )) {
				depth++;
			}
		}

		GamesInProgress.delete( cl );
	}

	public static Bundle gameBundle( String fileName ) throws IOException {

		InputStream input = Game.instance.openFileInput( fileName );
		Bundle bundle = Bundle.read( input );
		input.close();

		return bundle;
	}

	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.depth = bundle.getInt( DEPTH );
		info.challenges = (bundle.getInt( CHALLENGES ) != 0);
		if (info.depth == -1) {
			info.depth = bundle.getInt( "maxDepth" );	// FIXME
		}
		Hero.preview( info, bundle.getBundle( HERO ) );
	}

	public static void fail( Class cause ) {
		if (hero.belongings.getItem( Ankh.class ) == null) {
			Rankings.INSTANCE.submit( false, cause );
		}
	}

	public static void win( Class cause ) {

		hero.belongings.identify();

		if (challenges != 0) {
			Badges.validateChampion();
		}

		Rankings.INSTANCE.submit( true, cause );
	}

	public static void observe() {

		if (level == null) {
			return;
		}

		level.updateFieldOfView( hero );
		System.arraycopy( Level.fieldOfView, 0, visible, 0, visible.length );

		BArray.or( level.visited, visible, level.visited );

		GameScene.afterObserve();
	}

	private static boolean[] passable = new boolean[Level.LENGTH];

	public static int findPath( Char ch, int from, int to, boolean pass[], boolean[] visible ) {

		if (Level.adjacent( from, to )) {
			return Actor.findChar( to ) == null && (pass[to] || Level.avoid[to]) ? to : -1;
		}

		if (ch.flying || ch.buff( Amok.class ) != null) {
			BArray.or( pass, Level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Level.LENGTH );
		}

		for (Char c : Actor.chars()) {
			if (visible[c.pos]) {
				passable[c.pos] = false;
			}
		}

		return PathFinder.getStep( from, to, passable );

	}

	public static int flee( Char ch, int cur, int from, boolean pass[], boolean[] visible ) {

		if (ch.flying) {
			BArray.or( pass, Level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Level.LENGTH );
		}

		for (Char c : Actor.chars()) {
			if (visible[c.pos]) {
				passable[c.pos] = false;
			}
		}
		passable[cur] = true;

		return PathFinder.getStepBack( cur, from, passable );

	}

}
