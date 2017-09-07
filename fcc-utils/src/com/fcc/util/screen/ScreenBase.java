package com.fcc.util.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * @author goran
 */
public interface ScreenBase extends Screen {

    InputProcessor getInputProcessor();
}
