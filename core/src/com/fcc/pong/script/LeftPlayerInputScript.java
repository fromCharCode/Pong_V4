package com.fcc.pong.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.entity.Paddle;
import com.fcc.util.entity.script.EntityScriptBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 28.08.2017.
 */
public class LeftPlayerInputScript extends EntityScriptBase<Paddle> {

    // == attributes ==
    private float velocity;

    @Override
    public void added(Paddle entity) {
        super.added(entity);
    }

    @Override
    public void update(float delta) {

        velocity = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocity = (GameConfig.PADDLE_VELOCITY_Y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            velocity = (-GameConfig.PADDLE_VELOCITY_Y);
        }

        entity.setVelocityY(velocity);
    }
}
