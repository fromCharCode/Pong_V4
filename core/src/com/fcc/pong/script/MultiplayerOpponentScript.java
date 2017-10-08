package com.fcc.pong.script;

import com.fcc.pong.entity.Paddle;
import com.fcc.util.entity.script.EntityScriptBase;
import lombok.Getter;
import lombok.Setter;

public class MultiplayerOpponentScript extends EntityScriptBase<Paddle> {

    // == attributes ==
    @Getter
    @Setter
    private static float yPosition = 0F;

    @Override
    public void added(Paddle entity) {
        super.added(entity);
    }

    @Override
    public void update(float delta) {
        entity.setVelocityY(0F);
        entity.setY(yPosition);
    }
}
