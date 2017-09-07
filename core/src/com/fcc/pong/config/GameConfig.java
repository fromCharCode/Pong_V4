package com.fcc.pong.config;

import com.fcc.util.viewport.ViewportConfig;
import com.fcc.util.viewport.ViewportConfigBuilder;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public final class GameConfig {

    // == constants ==
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 768;

    // WORLD
    public static final float WORLD_WIDTH = 20f;
    public static final float WORLD_HEIGHT = 12f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;

    // HUD
    public static final float HUD_WIDTH = 1280f;
    public static final float HUD_HEIGHT = 768f;

    // PADDLE
    public static final float PADDLE_WIDTH = 0.5f;
    public static final float PADDLE_MIN_HEIGHT = 1.2f;
    public static final float PADDLE_START_HEIGHT = 3f;
    public static final float PADDLE_MAX_HEIGHT = 4.8f;

    public static final float LEFT_PADDLE_START_X = 1f;
    public static final float RIGHT_PADDLE_START_X = WORLD_WIDTH - LEFT_PADDLE_START_X - PADDLE_WIDTH;
    public static final float PADDLE_START_Y = (WORLD_HEIGHT - PADDLE_START_HEIGHT) / 2f;

    public static final float PADDLE_VELOCITY_Y = 12f;

    // BALL
    public static final float BALL_SIZE = 0.6f;
    public static final float BALL_START_X = (GameConfig.WORLD_WIDTH - BALL_SIZE) / 2f;
    public static final float BALL_START_Y = (GameConfig.WORLD_HEIGHT - BALL_SIZE) / 2f;

    public static final float BALL_START_SPEED = 8f;
    public static final float BALL_MIN_SPEED = 10f;
    public static final float BALL_MAX_SPEED = 20f;
    public static final float BALL_SPEED_FACTOR = 0.40f; // percentage

    public static final float INVI_BALL_SPEED = 10f;

    // GAME
    public static final int MAX_GAMES = 100;
    public static final int MIN_GAMES = 1;

    // Viewport config
    public static ViewportConfig VIEWPORT_CONFIG = new ViewportConfigBuilder()
            .worldSize(WORLD_WIDTH, WORLD_HEIGHT)
            .hudSize(HUD_WIDTH, HUD_HEIGHT)
            .build();

    // == constructors ==
    private GameConfig(){
    }

}
