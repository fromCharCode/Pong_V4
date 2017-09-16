package com.fcc.pong.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fcc.pong.PongGame;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.RegionNames;
import com.fcc.pong.common.SoundController;
import com.fcc.util.GdxUtils;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 27.08.2017.
 */
public class MenuScreen extends ScreenBaseAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;

    private SoundController soundController;

    private Stage stage;

    // == constructors ==
    public MenuScreen(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
        viewportManager = game.getViewportManager();
    }

    // == public methods ==
    @Override
    public void show() {
        stage = new Stage(viewportManager.getHudViewport(), game.getBatch());

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        soundController = (((PongGame) game).getSoundController());

        Table table = new Table(skin);
        table.defaults().space(20);
        table.setBackground(RegionNames.BACKGROUND);

        TextButton playButton = new TextButton("PLAY", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });


        TextButton optionButton = new TextButton("OPTIONS", skin);
        optionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                options();
            }
        });

        TextButton quitButton = new TextButton("QUIT", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        table.add(playButton).row();
        table.add(optionButton).row();
        table.add(quitButton).row();
        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        viewportManager.applyHud();


        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewportManager.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    // == private methods ==
    private void play(){
        game.setScreen(new PlayerScreen(game));
    }

    private void options(){
        game.setScreen(new OptionsScreen(game));
    }

    private void quit(){
        Gdx.app.exit();
    }
}
