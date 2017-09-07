package com.fcc.util.entity.script;

import com.badlogic.gdx.utils.Array;
import com.fcc.util.entity.EntityBase;

/**
 * Created by dasch on 03.08.2017.
 */
public class ScriptController {

    // == attributes ==
    private final Array<EntityScript<EntityBase>> scripts = new Array<EntityScript<EntityBase>>();
    private final com.fcc.util.entity.EntityBase entity;

    // == constructors ==
    public ScriptController(EntityBase entity) {
        this.entity = entity;
    }

    // == public methods ==
    public void update(float delta){
        for(int i = 0; i < scripts.size; i++){
            EntityScript script = scripts.get(i);
            if(script.isFinished()){
                scripts.removeIndex(i);
            } else {
                script.update(delta);
            }
        }
    }

    public void addScript(EntityScript<EntityBase> toAdd){
        scripts.add(toAdd);
        toAdd.added(entity);
    }

    public void removeScript(EntityScript<EntityBase> toRemove){
        scripts.removeValue(toRemove, true);
        toRemove.removed(entity);
    }
}
