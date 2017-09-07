package com.fcc.pong.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.fcc.pong.config.GameConfig;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class EntityFactory {

    // == attributes ==
    private final AssetManager assetManager;    // DON'T DELETE: NECESSARY FOR EFFECTS AND ANIMATIONS

    // == constructors ==
    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;

        init();
    }

    // == init ==
    private void init(){
        // TODO: add particles- init here
    }

    // == public methods ==
    public Paddle createLeftPaddle(){
        Paddle leftPaddle = new Paddle();
        leftPaddle.setPosition(GameConfig.LEFT_PADDLE_START_X, GameConfig.PADDLE_START_Y);
        leftPaddle.setSize(GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_START_HEIGHT);
        return leftPaddle;
    }

    public Paddle createRightPaddle(){
        Paddle rightPaddle = new Paddle();
        rightPaddle.setPosition(GameConfig.RIGHT_PADDLE_START_X, GameConfig.PADDLE_START_Y);
        rightPaddle.setSize(GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_START_HEIGHT);
        return rightPaddle;
    }

    public Ball createBall(){
        Ball ball = new Ball();
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y);
        ball.setSize(GameConfig.BALL_SIZE);
        return ball;
    }
}
