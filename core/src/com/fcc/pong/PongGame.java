package com.fcc.pong;

import com.fcc.pong.common.GameType;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.screen.LoadingScreen.LoadingScreen;
import com.fcc.util.ads.AdController;
import com.fcc.util.game.GameBase;
import lombok.Getter;
import lombok.Setter;

public class PongGame extends GameBase {

    // == attributes ==
    @Getter
    @Setter
    private GameType gameType;

    @Getter
    private static PongGame instance;

    // constructor ==
    public PongGame(AdController adController){
        super(adController, GameConfig.VIEWPORT_CONFIG);
        instance = this;
    }

    @Override
    public void postCreate() {
        setScreen(new LoadingScreen());
    }
}
