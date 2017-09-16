package com.fcc.pong;

import com.fcc.pong.common.GameType;
import com.fcc.pong.common.SoundController;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.screen.LoadingScreen.LoadingScreen;
import com.fcc.util.ads.AdController;
import com.fcc.util.game.GameBase;

public class PongGame extends GameBase {

    // == attributes ==
    private GameType gameType;
    private SoundController soundController;

    // constructor ==
    public PongGame(AdController adController){
        super(adController, GameConfig.VIEWPORT_CONFIG);
    }

    @Override
    public void postCreate() {
        setScreen(new LoadingScreen(this));

    }

    public SoundController getSoundController() {
        return soundController;
    }

    public void setSoundController(SoundController soundController) {
        this.soundController = soundController;
    }
}
