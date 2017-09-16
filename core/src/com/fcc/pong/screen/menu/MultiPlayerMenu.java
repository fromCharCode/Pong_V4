package com.fcc.pong.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fcc.pong.PongGame;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.RegionNames;
import com.fcc.util.GdxUtils;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 14.09.2017.
 */
public class MultiPlayerMenu extends ScreenBaseAdapter {

    private final GameBase game;
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;

    private Stage stage;

    public MultiPlayerMenu(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
        viewportManager = game.getViewportManager();
    }

    @Override
    public void show() {
        stage = new Stage(viewportManager.getHudViewport(), game.getBatch());

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        // main table
        Table table = new Table(skin);
        table.defaults().space(40);
        table.setBackground(RegionNames.BACKGROUND);

        // text field
        TextField inputField = new TextField("", skin);
        inputField.setMessageText("enter IP");

        // connect button
        TextButton connectButton = new TextButton("CONNECT", skin);


        // host button
        TextButton hostButton = new TextButton("HOST", skin);

        // back button
        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayerScreen(game));
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
}
