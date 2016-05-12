package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AssassinSprite;
import com.watabou.utils.Random;

public class Assassin extends Mob {

    {
        name = "assassin";
        spriteClass = AssassinSprite.class;

        HP = HT = 30;
        defenseSkill = 10;

        EXP = 6;
        maxLvl = 15;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(3, 10);
    }

    @Override
    public int attackSkill( Char target ) {
        return 14;
    }

    @Override
    public int dr() {
        return 5;
    }

    @Override
    public String description() {
        return
                "A trainee Assassin, eager to make his first kill so he can complete his training.";
    }
}
