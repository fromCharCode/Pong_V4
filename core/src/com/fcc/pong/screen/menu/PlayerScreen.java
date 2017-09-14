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
import com.fcc.pong.common.GameType;
import com.fcc.pong.common.SoundController;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.screen.game.GameScreen;
import com.fcc.pong.screen.transitions.ScreenTransitions;
import com.fcc.util.GdxUtils;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 27.08.2017.
 */
public class PlayerScreen extends ScreenBaseAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;
    private final SoundController soundController;

    private GameType gameType;

    private Stage stage;
    private int amount = 3;
    private Label amountLabel;

    // == constructors ==
    PlayerScreen(GameBase game, SoundController soundController){
        this.game = game;
        assetManager = game.getAssetManager();
        viewportManager = game.getViewportManager();
        this.soundController = soundController; // TODO: add menu music later
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

        // table for rounds label
        Table labelTable = new Table(skin);
        Label roundsLabel = new Label("ROUNDS", skin);
        labelTable.add(roundsLabel);

        // table for changing amount of rounds
        Table topTable = new Table(skin);
        TextButton lessButton = new TextButton("<", skin);
        amountLabel = new Label("" + amount, skin);
        TextButton moreButton = new TextButton(">", skin);

        lessButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                amount--;
                clampAmount();
                updateLabel();
            }
        });

        moreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                amount++;
                clampAmount();
                updateLabel();
            }
        });

        topTable.add(lessButton).left().expandX();
        topTable.add(amountLabel).center().pad(20).expandX();
        topTable.add(moreButton).right().expandX();
        topTable.row();

        // table for beginning or going back
        Table actionTable = new Table(skin);
        actionTable.defaults().space(20);

        TextButton soloButton = new TextButton("1 PLAYER", skin);
        TextButton localMultiPlayerButton = new TextButton("2 PLAYER", skin);

        TextButton multiPlayerButton = new TextButton("MULTIPLAYER", skin);

        TextButton backButton = new TextButton("BACK", skin);

        soloButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameType = GameType.SINGLE_PLAYER;
                play();
            }
        });

        localMultiPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameType = GameType.MULTI_PLAYER;
                play();
            }
        });

        multiPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MultiPlayerMenu(game));
            }
        });
        multiPlayerButton.setDisabled(true); // TODO: remove

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        actionTable.add(soloButton).row();
        actionTable.add(localMultiPlayerButton).row();
        actionTable.add(multiPlayerButton).row();
        actionTable.add(backButton);

        // add everything to main table
        table.add(labelTable).expandX().row();
        table.add(topTable).expandX().row();
        table.add(actionTable).bottom();
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
    private void updateLabel(){
        amountLabel.setText("" + amount);
    }

    private void play(){
        game.setScreen(new GameScreen(game, amount, gameType, soundController), ScreenTransitions.SLIDE);
    }

    private void back(){
        game.setScreen(new MenuScreen(game));
    }

    private void clampAmount(){
        if(amount <= 0){
            amount = GameConfig.MAX_GAMES;
        } else if(amount >= 101){
            amount = GameConfig.MIN_GAMES;
        }
    }
}
