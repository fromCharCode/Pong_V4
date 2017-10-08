package com.fcc.pong.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fcc.pong.PongGame;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 09.09.2017.
 */
public class GameManager {

    // == constants ==
    public static final GameManager INSTANCE = new GameManager();

    private static final String VOLUME_KEY = "volume";
    private static final String ATLAS_KEY = "atlas";
    private static final String ATLAS_POINTER_KEY = "atlasPointer";

    // == attributes ==
    private float volume;
    private String atlas;
    private int atlasPoint;
    // possibly not necessary
    private String atlasPath = "gameplay/";


    private Preferences prefs;

    // == constructors ==
    private GameManager(){
        prefs = Gdx.app.getPreferences(PongGame.class.getSimpleName());

        atlasPoint = prefs.getInteger(ATLAS_POINTER_KEY, 1);
        volume = prefs.getFloat(VOLUME_KEY, 1f);
        atlas = prefs.getString(ATLAS_KEY, "default.atlas");
    }

    // NOTE: no prefs.flush -> Volume just gets temporary saved, but is always 1.0f at a restart of the programm

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public void setAtlas(String atlas) {
        this.atlas = atlas;
    }

    public String getAtlas() {
        return atlas;
    }

    public void setAtlasPoint(int atlasPoint) {
        this.atlasPoint = atlasPoint;
    }

    public int getAtlasPoint() {
        return atlasPoint;
    }
}
