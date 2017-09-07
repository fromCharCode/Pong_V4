package com.fcc.util.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by fromCharCode on 13.08.2017.
 */
public class BasicAssetErrorListener implements AssetErrorListener {

    // == constants ==
    private static final Logger log = new Logger(BasicAssetErrorListener.class.getSimpleName(), Logger.DEBUG);

    public static final BasicAssetErrorListener INSTANCE = new BasicAssetErrorListener();

    // == constructors ==
    private BasicAssetErrorListener(){}

    @Override
    public void error(AssetDescriptor descriptor, Throwable throwable) {
        String message = "Error loading asset= " + descriptor.fileName + " path= " + descriptor.file.path() + " type= " + descriptor.type;
        log.error(message, throwable);
    }
}
