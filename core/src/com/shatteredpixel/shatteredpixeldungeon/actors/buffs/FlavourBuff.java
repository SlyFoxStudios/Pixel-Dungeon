
package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

//buff whose only internal logic is to wait and detach after a time.
public class FlavourBuff extends Buff {
	
	@Override
	public boolean act() {
		detach();
		return true;
	}

	//flavour buffs can all just rely on cooldown()
	protected String dispTurns() {
		//add one turn as buffs act last, we want them to end at 1 visually, even if they end at 0 internally.
		return dispTurns(cooldown()+1f);
	}
}
