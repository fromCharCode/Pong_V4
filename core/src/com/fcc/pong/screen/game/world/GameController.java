package com.fcc.pong.screen.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.fcc.pong.entity.Ball;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class GameController {

    // == attributes ==
    private final GameWorld gameWorld;
    private final GameRenderer renderer;

    // == constructors ==
    public GameController(GameWorld gameWorld, GameRenderer renderer) {
        this.gameWorld = gameWorld;
        this.renderer = renderer;
    }

    // == public methods ==
    public void update(float delta){
        handleDebugInput();
        handlePauseInput();

        Ball ball = gameWorld.getBall();

        if(ball.isNotActive() && (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) ){
            gameWorld.activateBall(true);
        }

        gameWorld.update(delta);
    }

    // == private methods ==
    private void handleDebugInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.F5)){
            gameWorld.toggleDrawGrid();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F6)){
            gameWorld.toggleDrawDebug();
        }
    }

    private void handlePauseInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            gameWorld.togglePaused();
        }
    }
}
