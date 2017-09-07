package com.fcc.pong.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.fcc.pong.assets.AssetDescriptors;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 27.08.2017.
 */
public class SoundController {

    // == attributes ==
    private final AssetManager assetManager;

    private Sound hit_right;
    private Sound hit_left;
    private Sound lose;
    private float volume = 1f;

    // == constructors ==
    public SoundController(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }

    // == init ==
    private void init(){
        hit_right = assetManager.get(AssetDescriptors.HIT_RIGHT);
        hit_left = assetManager.get(AssetDescriptors.HIT_LEFT);
        lose = assetManager.get(AssetDescriptors.LOSE);
    }

    // == public methods ==
    public void hitRight(){
        hit_right.play(volume);
    }

    public void hitLeft(){
        hit_left.play(volume);
    }

    public void lose(){
        lose.play(volume);
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
