package com.fcc.pong.screen.LoadingScreen;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.screen.banner.BannerScreen;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.loading.LoadingScreenBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 26.08.2017.
 */
public class LoadingScreen extends LoadingScreenBase {

    public LoadingScreen(GameBase game) {
        super(game);
    }

    @Override
    protected Array<AssetDescriptor> getAssetDescriptors() {
        return AssetDescriptors.ALL;
    }

    @Override
    protected void onLoadDone() {
        game.setScreen(new BannerScreen(game));
    }

}
