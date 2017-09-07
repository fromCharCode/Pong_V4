package com.fcc.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fcc.pong.PongGame;
import com.fcc.pong.config.GameConfig;
import com.fcc.util.ads.NullAdController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = GameConfig.WIDTH;
        config.height = GameConfig.HEIGHT;

		new LwjglApplication(new PongGame(NullAdController.INSTANCE), config);
	}
}
