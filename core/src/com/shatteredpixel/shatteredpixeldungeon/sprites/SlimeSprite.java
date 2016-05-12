package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class SlimeSprite extends MobSprite {
    public SlimeSprite() {
        super();

        texture(Assets.SLIME);

        TextureFilm frames = new TextureFilm(texture, 20, 14);

        idle = new Animation(10, true);
        idle.frames(frames, 2, 1, 0, 0, 1);

        run = new Animation(10, true);
        run.frames(frames, 3, 2, 1, 2);

        attack = new Animation(10, false);
        attack.frames(frames, 8, 9, 4, 9, 10);

        die = new Animation(10, false);
        die.frames(frames, 5, 6, 7);

        play(idle);
    }
}
