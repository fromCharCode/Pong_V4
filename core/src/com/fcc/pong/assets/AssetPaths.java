package com.fcc.pong.assets;

import com.badlogic.gdx.Gdx;
import com.fcc.pong.common.GameManager;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 26.08.2017.
 */
public class AssetPaths {

    static String gamePlayPath = "gameplay/";

    static final String SCORE_FONT = "ui/font/font.fnt";

    static String GAME_PLAY = ( gamePlayPath + GameManager.INSTANCE.getAtlas()); // + ".atlas" not necessary

    static final String BANNER = "banner/intro.png";

    static final String BACKGROUND = "background/background.png";

    static final String SKIN = "ui/skin.json";

    static final String HIT_RIGHT = "sounds/hit_right.wav";

    static final String HIT_LEFT = "sounds/hit_left.wav";

    static final String LOSE = "sounds/lose.wav";

    private AssetPaths(){}

    public static void setGamePlay(String gamePlay) {
        GAME_PLAY = gamePlayPath + gamePlay;
        System.out.println("atlas=" + GAME_PLAY);
    }
}
