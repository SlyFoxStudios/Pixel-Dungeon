package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class AssassinSprite extends MobSprite {

    public AssassinSprite() {
        super();

        texture( Assets.MONK );

        TextureFilm frames = new TextureFilm( texture, 15, 14 );

        idle = new MovieClip.Animation( 18, true );
        idle.frames( frames, 35, 34, 35, 36 );

        run = new MovieClip.Animation( 18, true );
        run.frames( frames, 45, 46, 47, 48, 49, 50 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, 37, 38, 37, 38 );

        die = new MovieClip.Animation( 15, false );
        die.frames( frames, 35, 41, 42, 42, 43, 44 );

        play( idle );
    }
}
