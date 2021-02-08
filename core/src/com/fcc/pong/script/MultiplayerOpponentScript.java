package com.fcc.pong.script;

import com.fcc.pong.entity.Paddle;
import com.fcc.util.entity.script.EntityScriptBase;

public class MultiplayerOpponentScript extends EntityScriptBase<Paddle> {

    // == attributes ==
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

    public static void setyPosition(float yPosition) {
        MultiplayerOpponentScript.yPosition = yPosition;
    }

    public static float getyPosition() {
        return yPosition;
    }
}
