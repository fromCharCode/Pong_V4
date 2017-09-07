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
        hit_right.play();
    }

    public void hitLeft(){
        hit_left.play();
    }

    public void lose(){
        lose.play();
    }
}
