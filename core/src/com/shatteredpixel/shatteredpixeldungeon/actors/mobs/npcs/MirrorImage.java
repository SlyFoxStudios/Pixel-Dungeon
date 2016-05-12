
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MirrorSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class MirrorImage extends NPC {
	
	{
		spriteClass = MirrorSprite.class;
		
		state = HUNTING;
	}
	
	public int tier;
	
	private int attack;
	private int damage;
	
	private static final String TIER	= "tier";
	private static final String ATTACK	= "attack";
	private static final String DAMAGE	= "damage";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( TIER, tier );
		bundle.put( ATTACK, attack );
		bundle.put( DAMAGE, damage );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		tier = bundle.getInt( TIER );
		attack = bundle.getInt( ATTACK );
		damage = bundle.getInt( DAMAGE );
	}
	
	public void duplicate( Hero hero ) {
		tier = hero.tier();
		attack = hero.attackSkill( hero );
		damage = hero.damageRoll();
	}
	
	@Override
	public int attackSkill( Char target ) {
		return attack;
	}
	
	@Override
	public int damageRoll() {
		return damage;
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		int dmg = super.attackProc( enemy, damage );

		destroy();
		sprite.die();
		
		return dmg;
	}
	
	protected Char chooseEnemy() {
		
		if (enemy == null || !enemy.isAlive()) {
			HashSet<Mob> enemies = new HashSet<>();
			for (Mob mob:Dungeon.level.mobs) {
				if (mob.hostile && Level.fieldOfView[mob.pos]) {
					enemies.add( mob );
				}
			}
			
			enemy = enemies.size() > 0 ? Random.element( enemies ) : null;
		}
		
		return enemy;
	}
	
	@Override
	public CharSprite sprite() {
		CharSprite s = super.sprite();
		((MirrorSprite)s).updateArmor( tier );
		return s;
	}

	@Override
	public void interact() {
		
		int curPos = pos;
		
		moveSprite( pos, Dungeon.hero.pos );
		move( Dungeon.hero.pos );
		
		Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
		Dungeon.hero.move( curPos );
		
		Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
		Dungeon.hero.busy();
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( ToxicGas.class );
		IMMUNITIES.add( Burning.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}