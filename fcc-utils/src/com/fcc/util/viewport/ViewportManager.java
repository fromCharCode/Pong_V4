package com.fcc.util.viewport;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Project: SimplePlatformer
 * Created by fromCharCode on 20.08.2017.
 */
public class ViewportManager {

    // == attributes ==
    private final ViewportConfig viewportConfig;

    private OrthographicCamera hudCamera;
    private OrthographicCamera worldCamera;

    private Viewport hudViewport;
    private Viewport worldViewport;

    // == constructors ==
    public ViewportManager(ViewportConfig viewportConfig) {
        this.viewportConfig = viewportConfig;
        init();
    }

    // == init ==
    private void init(){
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(viewportConfig.hudWidth, viewportConfig.hudHeight, hudCamera);
        hudViewport.apply(true);

        worldCamera = new OrthographicCamera();
        worldViewport = new FitViewport(viewportConfig.worldWidth, viewportConfig.worldHeight, worldCamera);
        worldViewport.apply(true);
    }

    // == public methods ==
    public void resize(int screenWidth, int screenHeight){
        hudViewport.update(screenWidth, screenHeight, true);
        worldViewport.update(screenWidth, screenHeight, true);
    }

    public void applyHud(){
        hudViewport.apply();
    }

    public Matrix4 getHudCombined(){
        return hudCamera.combined;
    }

    public OrthographicCamera getHudCamera(){
        return hudCamera;
    }

    public Viewport getHudViewport() {
        return hudViewport;
    }

    public void applyWorld(){
        worldViewport.apply();
    }

    public Matrix4 getWorldCombined(){
        return worldCamera.combined;
    }

    public OrthographicCamera getWorldCamera() {
        return worldCamera;
    }

    public Viewport getWorldViewport() {
        return worldViewport;
    }

    public void debugPixelsPerUnit(){
        ViewportUtils.debugPixelsPerUnit(worldViewport);
        ViewportUtils.debugPixelsPerUnit(hudViewport);
    }

    public Vector2 getWorldCameraPosition(){
        return new Vector2(worldCamera.position.x, worldCamera.position.y);
    }

    public Vector2 screenToWorld(Vector2 screenCoordinates){
        return worldViewport.unproject(screenCoordinates);
    }

    public void drawWorldGrid(ShapeRenderer shapeRenderer){
        ViewportUtils.drawGrid(worldViewport, shapeRenderer);
    }

    public float getHudWidth(){
        return viewportConfig.hudWidth;
    }

    public float getHudHeight(){
        return viewportConfig.hudHeight;
    }

    public float getWorldWidth(){
        return viewportConfig.worldWidth;
    }

    public float getWorldHeight(){
        return viewportConfig.worldHeight;
    }
}
