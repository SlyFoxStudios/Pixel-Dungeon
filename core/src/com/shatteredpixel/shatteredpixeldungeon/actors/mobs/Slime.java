package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SlimeSprite;
import com.watabou.utils.Random;

public class Slime extends Mob {

    {
        name = "green slime";
        spriteClass = SlimeSprite.class;

        HP = HT = 18;
        defenseSkill = 6;

        EXP = 4;
        maxLvl = 11;

        loot = new PotionOfToxicGas();
        lootChance = 0.167f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(3, 7);
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int dr() {
        return 5;
    }

    @Override
    public String description() {
        return
                "Slimes look like icky little piles of goo, but they can pack a bite." +
                        "They bash their prey around and then engulf them to eat.";
    }

}
