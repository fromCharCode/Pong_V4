package com.fcc.pong.screen.LoadingScreen;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;
import com.fcc.pong.PongGame;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.AssetPaths;
import com.fcc.pong.common.GameManager;
import com.fcc.pong.screen.banner.BannerScreen;
import com.fcc.util.screen.loading.LoadingScreenBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 26.08.2017.
 */
public class LoadingScreen extends LoadingScreenBase {

    public LoadingScreen() {
        super(PongGame.getInstance());
    }

    @Override
    protected Array<AssetDescriptor> getAssetDescriptors() {
        return AssetDescriptors.ALL;
    }

    @Override
    protected void onLoadDone() {
        // loading default atlas
        AssetPaths.setGamePlay(GameManager.INSTANCE.getAtlas());
        AssetDescriptors.setGamePlay();
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.finishLoading();

        game.setScreen(new BannerScreen());
    }

}
