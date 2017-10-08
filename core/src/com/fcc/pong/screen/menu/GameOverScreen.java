package com.fcc.pong.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fcc.pong.PongGame;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.RegionNames;
import com.fcc.pong.screen.transitions.ScreenTransitions;
import com.fcc.util.GdxUtils;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 27.08.2017.
 */
public class GameOverScreen extends ScreenBaseAdapter {

    // == attributes ==
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;
    private final String LabelText;

    private Stage stage;

    // == constructors ==
    public GameOverScreen(String labelText) {
        assetManager = PongGame.getInstance().getAssetManager();
        viewportManager = PongGame.getInstance().getViewportManager();
        LabelText = labelText;
    }

    // == public methods ==
    @Override
    public void show() {
        stage = new Stage(viewportManager.getHudViewport(), PongGame.getInstance().getBatch());

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        Table table = new Table(skin);
        table.defaults().space(40);
        table.setBackground(RegionNames.BACKGROUND);

        Label winnerLabel = new Label(LabelText, skin);

        TextButton menuButton = new TextButton("MENU", skin);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menu();
            }
        });

        table.add(winnerLabel).expandX().row();
        table.add(menuButton).row();
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
    private void menu(){
        PongGame.getInstance().setScreen(new MenuScreen(), ScreenTransitions.SLIDE);
    }
}
