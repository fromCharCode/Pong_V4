package com.fcc.pong;

import com.fcc.pong.common.GameType;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.screen.LoadingScreen.LoadingScreen;
import com.fcc.util.ads.AdController;
import com.fcc.util.game.GameBase;

public class PongGame extends GameBase {

    // == attributes ==
    private GameType gameType;

    private static PongGame instance;

    // constructor ==
    public PongGame(AdController adController){
        super(adController, GameConfig.VIEWPORT_CONFIG);
        instance = this;
    }

    @Override
    public void postCreate() {
        setScreen(new LoadingScreen(this));
    }

    public static com.fcc.pong.PongGame getInstance() {
        return instance;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }
}
