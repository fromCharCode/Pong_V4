package com.fcc.pong.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fcc.pong.PongGame;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.RegionNames;
import com.fcc.pong.common.NetworkManager;
import com.fcc.pong.common.SoundController;
import com.fcc.pong.screen.game.GameScreen;
import com.fcc.pong.screen.transitions.ScreenTransitions;
import com.fcc.util.GdxUtils;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;
import lombok.Getter;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 14.09.2017.
 */
public class MultiPlayerMenu extends ScreenBaseAdapter {

    private final AssetManager assetManager;
    private final ViewportManager viewportManager;

    @Getter
    private static int amount;

    @Getter
    private static String message;

    @Getter
    private static SoundController soundController;

    private Stage stage;

    public MultiPlayerMenu(int amount, String message, SoundController soundController) {
        this.amount = amount;
        this.message = message;
        this.soundController = soundController;
        assetManager = PongGame.getInstance().getAssetManager();
        viewportManager = PongGame.getInstance().getViewportManager();
    }

    @Override
    public void show() {
        stage = new Stage(viewportManager.getHudViewport(), PongGame.getInstance().getBatch());

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        // main table
        Table table = new Table(skin);
        table.defaults().space(40);
        table.setBackground(RegionNames.BACKGROUND);

        // status label
        Label statusLabel = new Label(message == null ? "" : message, skin);

        // text field
        TextField inputField = new TextField("", skin);
        inputField.setMessageText("enter IP");

        // connect button
        TextButton connectButton = new TextButton("CONNECT", skin);
        connectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                NetworkManager.multiPlayerConnect(inputField.getText());
            }
        });

        // host button
        TextButton hostButton = new TextButton("HOST", skin);
        hostButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                NetworkManager.multiPlayerHost();
            }
        });
        // back button
        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PongGame.getInstance().setScreen(new MenuScreen());
            }
        });


        table.add(inputField).row();
        table.add(connectButton).row();
        table.add(hostButton).row();
        table.add(backButton).row();

        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
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

    private void play() {
        PongGame.getInstance().setScreen(new GameScreen(amount, soundController), ScreenTransitions.SLIDE);
    }

}
