package com.fcc.pong.screen.transitions;

import com.badlogic.gdx.math.Interpolation;
import com.fcc.util.screen.transition.ScreenTransition;
import com.fcc.util.screen.transition.impl.FadeScreenTransition;
import com.fcc.util.screen.transition.impl.ScaleScreenTransition;
import com.fcc.util.screen.transition.impl.SlideScreenTransition;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 27.08.2017.
 */
public class ScreenTransitions {

    public static final ScreenTransition FADE = new FadeScreenTransition(1f);

    public static final ScreenTransition SCALE = new ScaleScreenTransition(1.5f, false, Interpolation.swingIn); // in case of need

    public static final ScreenTransition SLIDE = new SlideScreenTransition(1f, false);

}
