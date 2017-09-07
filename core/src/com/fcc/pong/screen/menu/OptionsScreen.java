package com.fcc.pong.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.RegionNames;
import com.fcc.pong.common.SoundController;
import com.fcc.util.GdxUtils;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 07.09.2017.
 */
class OptionsScreen extends ScreenBaseAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;
    private final SoundController soundController;

    private Stage stage;

    // == constructors ==
    OptionsScreen(GameBase game, SoundController soundController) {
        this.game = game;
        assetManager = game.getAssetManager();
        viewportManager = game.getViewportManager();
        this.soundController = soundController;
    }

    // == public methods ==
    @Override
    public void show() {
        stage = new Stage(viewportManager.getHudViewport(), game.getBatch());

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        // main table
        Table table = new Table(skin);
        table.defaults().space(40);
        table.setBackground(RegionNames.BACKGROUND);


        // SoundBar?
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle(); // first param slider bg // second knob
        final Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, sliderStyle); // we need to add a slider
        volumeSlider.setValue(soundController.getVolume());
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundController.setVolume(volumeSlider.getValue());
            }
        });


        // meeeeeh. let's see



        // back button
        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(volumeSlider).row();
        table.add(backButton);
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
}
