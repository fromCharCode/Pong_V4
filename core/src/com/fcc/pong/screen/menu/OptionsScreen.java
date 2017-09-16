package com.fcc.pong.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.AssetPaths;
import com.fcc.pong.assets.RegionNames;
import com.fcc.pong.common.GameManager;
import com.fcc.pong.common.SoundController;
import com.fcc.util.GdxUtils;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.ScreenBaseAdapter;
import com.fcc.util.viewport.ViewportManager;

import java.io.File;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 07.09.2017.
 */
class OptionsScreen extends ScreenBaseAdapter {

    // == constants ==
    private static final Logger log = new Logger(OptionsScreen.class.getSimpleName(), Logger.DEBUG); // TODO: remove when done

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;
    private final SoundController soundController;
    private AssetPaths assetPaths;

    private final Array<AssetDescriptor<TextureAtlas>> textureAtlases = new Array<AssetDescriptor<TextureAtlas>>();

    private AssetDescriptor<TextureAtlas> currentAtlas; // TODO: start working here

    private Stage stage;

    private Label volumeLabel;
    private Label texturePackLabel;
    private Label currentPackLabel;

    private  FileHandle[] files;
    private FileHandle[] file;
    Array<File> fileArray = new Array<File>();


    private int i = 1;
    private int filePointer = 1;

    // == constructors ==
    OptionsScreen(GameBase game, SoundController soundController) {
        this.game = game;
        assetManager = game.getAssetManager();
        viewportManager = game.getViewportManager();
        this.soundController = soundController;
        currentAtlas = AssetDescriptors.GAME_PLAY;
        init();
    }

    // == init ==
    private void init(){

        files = Gdx.files.internal("gameplay/").list();

        for (FileHandle file: files){
            log.debug("FileName: " + file.name());
            log.debug(file.extension());
            if (file.extension().toString().equals("atlas")) {
                log.debug("it should add the file to the array");
                fileArray.add(file.file());

            }
        }

        i = GameManager.INSTANCE.getAtlasPoint();
        /*for(int j = 0; j <= files.length; j++){
            if (files[j].extension().equals("atlas")){
//                log.debug("" + file.length);
            }
        }*/
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

        // texture pack label
        texturePackLabel = new Label("TEXTURE PACK", skin);

        /*for(int j = 1; j < files.length; j++){
            if (files[j].extension().equals("atlas")){
                files[j].delete();
                log.debug("" + files.length);
            }
        }*/

        currentPackLabel = new Label("" + fileArray.get(i).getName(), skin); // NOTE: this shall show the actual texture pack

        TextButton nextButton = new TextButton(">", skin);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                i++;
                clampI();
                updateLabel();
            }
        });

        Table texturePackTable = new Table(skin);
        TextButton prevButton = new TextButton("<", skin);
        prevButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                i--;
                clampI();
                updateLabel();
            }
        });

        texturePackTable.add(prevButton).left().expandX();
        texturePackTable.add(currentPackLabel).center().pad(20).expandX();
        texturePackTable.add(nextButton).right().expandX();


        // Sound label
        volumeLabel = new Label("VOLUME", skin);

        // SoundBar
        final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin); // we need to add a slider
        volumeSlider.setValue(GameManager.INSTANCE.getVolume());
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.INSTANCE.setVolume(volumeSlider.getValue());
            }
        });


        // meeeeeh. let's see



        // back button
        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                load();
                unLoad();
                GameManager.INSTANCE.setAtlas(fileArray.get(i).getName());
                assetPaths.setGamePlay(GameManager.INSTANCE.getAtlas());
                AssetDescriptors.setGamePlay();
                currentAtlas = AssetDescriptors.GAME_PLAY;
                load();
                game.setScreen(new MenuScreen(game));
            }
        });

        // add everything to table
        table.add(texturePackLabel).row();
        table.add(texturePackTable).row();
        table.add(volumeLabel).row();
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

    public AssetDescriptor<TextureAtlas> getCurrentAtlas() {
        return currentAtlas;
    }

    // == private methods ==

    private void unLoad(){
        if(currentAtlas != null){
            assetManager.unload(currentAtlas.fileName);
        }
    }

    private void load(){
        assetManager.load(currentAtlas);
        // wait until loading of current level is finished
        assetManager.finishLoading();
    }

    private void updateLabel(){
        GameManager.INSTANCE.setAtlasPoint(i);
        currentPackLabel.setText("" + fileArray.get(i).getName());
    }

    private void clampI(){
        if (i < 0){
            i = fileArray.size -1;   // -1 because length doesn't begin with 0
        } else if (i > fileArray.size - 1){
            i = 0;
        }
    }
}
