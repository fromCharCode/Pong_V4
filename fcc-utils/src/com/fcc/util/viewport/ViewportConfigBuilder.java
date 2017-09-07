package com.fcc.util.viewport;

/**
 * Project: SimplePlatformer
 * Created by fromCharCode on 20.08.2017.
 */
public class ViewportConfigBuilder {

    // == attributes ==
    private ViewportConfig viewportConfig = new ViewportConfig();

    // == constructors ==
    public ViewportConfigBuilder() {
    }

    // == public methods ==
    public ViewportConfigBuilder hudSize(float hudWidth, float hudHeight){
        viewportConfig.hudWidth = hudWidth;
        viewportConfig.hudHeight = hudHeight;
        return this;
    }

    public ViewportConfigBuilder worldSize(float worldWidth, float worldHeight){
        viewportConfig.worldWidth = worldWidth;
        viewportConfig.worldHeight = worldHeight;
        return this;
    }

    public ViewportConfig build(){
        return viewportConfig;
    }
}
