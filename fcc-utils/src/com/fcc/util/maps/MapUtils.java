package com.fcc.util.maps;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Logger;
import com.fcc.util.Validate;

import java.util.Iterator;

/**
 * Created by fromCharCode on 13.08.2017.
 */
public final class MapUtils {

    // == constants ==
    public static final Logger log = new Logger(MapUtils.class.getSimpleName(), Logger.DEBUG);
    public static final String LS = System.getProperty("line.separator");
    public static final String TAB = "\t";

    // == public methods ==
    public static void debugMapProperties(MapProperties mapProperties){
        Validate.notNull(mapProperties);

        StringBuilder sb = new StringBuilder();
        sb.append("properties= ").append(LS);

        for(Iterator<String> keyIterator = mapProperties.getKeys(); keyIterator.hasNext();){
            String key = keyIterator.next();
            Object value = mapProperties.get(key);

            String typeName = value.getClass().getSimpleName();
            String keyValueString = "key= " + key + " value= " + value + " type= " + typeName;

            sb.append(TAB);
            sb.append(keyValueString);

            if(keyIterator.hasNext()){
                sb.append(LS);
            }
        }

        log.debug(sb.toString());
    }

    // == constructors ==
    private MapUtils(){}

}
