package com.fcc.pong.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fcc.pong.PongGame;
import com.fcc.pong.common.SoundController;
import com.fcc.pong.entity.EntityFactory;
import com.fcc.pong.screen.game.world.GameController;
import com.fcc.pong.screen.game.world.GameRenderer;
import com.fcc.pong.screen.game.world.GameWorld;
import com.fcc.util.screen.ScreenBaseAdapter;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class GameScreen extends ScreenBaseAdapter{

    // == attributes ==
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final int amount;
    private final SoundController soundController;

    private GameWorld gameWorld;
    private GameRenderer renderer;
    private GameController controller;

    private EntityFactory factory;

    // == constructors ==
    public GameScreen(int amount, SoundController soundController) {
        assetManager = PongGame.getInstance().getAssetManager();
        batch = PongGame.getInstance().getBatch();
        this.amount = amount;
        this.soundController = soundController;
    }

    // public methods ==
    @Override
    public void show() {
        factory = new EntityFactory(assetManager);
        gameWorld = new GameWorld(soundController, factory, amount);
        renderer = new GameRenderer(gameWorld, batch, assetManager, PongGame.getInstance().getViewportManager());
        controller = new GameController(gameWorld, renderer);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
