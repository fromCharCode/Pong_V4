package com.fcc.pong.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 26.08.2017.
 */
public final class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT = new AssetDescriptor<BitmapFont>(AssetPaths.SCORE_FONT, BitmapFont.class);

    public static final AssetDescriptor<Texture> BANNER = new AssetDescriptor<Texture>(AssetPaths.BANNER, Texture.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY = new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>(AssetPaths.SKIN, Skin.class);

    public static final AssetDescriptor<Sound> HIT_RIGHT = new AssetDescriptor<Sound>(AssetPaths.HIT_RIGHT, Sound.class);

    public static final AssetDescriptor<Sound> HIT_LEFT = new AssetDescriptor<Sound>(AssetPaths.HIT_LEFT, Sound.class);

    public static final AssetDescriptor<Sound> LOSE = new AssetDescriptor<Sound>(AssetPaths.LOSE, Sound.class);

    // == all descriptors ==
    public static final Array<AssetDescriptor> ALL = new Array<AssetDescriptor>();

    // static init
    static {
        ALL.addAll(
                FONT,
                BANNER,
                GAME_PLAY,
                SKIN,
                HIT_RIGHT,
                HIT_LEFT,
                LOSE
        );
    }

    // == constructors ==
    private AssetDescriptors(){}
}
