package com.fcc.util.screen.banner;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fcc.util.GdxUtils;
import com.fcc.util.game.GameBase;
import com.fcc.util.screen.ScreenBaseAdapter;

/**
 * Created by fromCharCode on 15.08.2017.
 */
public abstract class BannerScreenBase extends ScreenBaseAdapter {

    // == constants ==
    public static final float DEFAULT_HUD_WIDTH = 640f;
    public static final float DEFAULT_HUD_HEIGHT = 480f;
    public static final float DEFAULT_DURATION = 3f;

    // == attributes ==
    private float hudWidth;
    private float hudHeight;
    private float duration;
    private Texture texture;

    protected final GameBase game;
    protected final AssetManager assetManager;
    protected final SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;

    private float time;

    private boolean changeScreen;

    // == constructors ==
    protected BannerScreenBase(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    // abstract methods ==
    protected abstract Texture getTexture();

    protected abstract void onBannerDone();

    // == public methods ==
    @Override
    public void show() {
        hudWidth = getHudWidth();
        hudHeight = getHudHeight();
        texture = getTexture();
        duration = getDuration();

        camera = new OrthographicCamera();
        viewport = new FitViewport(hudWidth, hudHeight, camera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        draw();

        batch.end();

        if(changeScreen){
            onBannerDone();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    // == private methods ==
    protected float getHudWidth(){
        return DEFAULT_HUD_WIDTH;
    }

    protected float getHudHeight(){
        return DEFAULT_HUD_HEIGHT;
    }

    protected float getDuration(){
        return DEFAULT_DURATION;
    }

    private void update(float delta){
        time += delta;

        if(time >= duration){
            changeScreen = true;
        }
    }

    private void draw(){
        batch.draw(texture, 0, 0, hudWidth, hudHeight);
    }
}
