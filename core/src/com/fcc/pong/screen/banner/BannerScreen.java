package com.fcc.pong.screen.banner;

import com.badlogic.gdx.graphics.Texture;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.screen.menu.MenuScreen;
import com.fcc.pong.screen.transitions.ScreenTransitions;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.banner.BannerScreenBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 27.08.2017.
 */
public class BannerScreen extends BannerScreenBase{

    public BannerScreen(GameBase game) {
        super(game);
    }

    @Override
    protected Texture getTexture() {
        return assetManager.get(AssetDescriptors.BANNER);
    }

    @Override
    protected void onBannerDone() {
        game.setScreen(new MenuScreen(game), ScreenTransitions.FADE);
    }

    @Override
    protected float getHudWidth() {
        return GameConfig.HUD_WIDTH;
    }

    @Override
    protected float getHudHeight() {
        return GameConfig.HUD_HEIGHT;
    }

    @Override
    protected float getDuration() {
        return 2.25f;
    }
}
