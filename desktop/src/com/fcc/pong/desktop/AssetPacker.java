package com.fcc.pong.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.FileProcessor;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.fcc.pong.PongGame;
import com.fcc.pong.config.GameConfig;
import com.fcc.util.ads.NullAdController;

public class AssetPacker {

    // == constants ==
    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";

    private static final String ASSETS_PATH = "core/assets";

    // == main ==
    public static void main(String[] args) {
        TexturePacker.process(RAW_ASSETS_PATH + "/gameplay", ASSETS_PATH + "/gameplay", "gameplay");
        TexturePacker.process(RAW_ASSETS_PATH + "/skin", ASSETS_PATH + "/ui", "skin");
    }

    private String getParentFolderName(){

        String parentFolderName = "";



        return parentFolderName;
    }
}
