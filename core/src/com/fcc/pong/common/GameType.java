package com.fcc.pong.common;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 28.08.2017.
 */
public enum GameType {
    SINGLE_PLAYER,
    MULTI_PLAYER;

    // == public methods ==
    public boolean isSinglePlayer(){
        return this == SINGLE_PLAYER;
    }

    public boolean isMultiPlayer(){
        return this == MULTI_PLAYER;
    }
}
