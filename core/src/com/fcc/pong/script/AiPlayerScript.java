package com.fcc.pong.script;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.entity.Ball;
import com.fcc.pong.entity.Paddle;
import com.fcc.util.entity.script.EntityScriptBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 28.08.2017.
 */
public class AiPlayerScript extends EntityScriptBase<Paddle> {

    // == attributes ==
    private Ball inviBall;
    private boolean paddleHitSettled;
    private boolean targetPointSettled;
    private boolean inviBallSettled;
    private boolean stop;
    private boolean hit;
    private float targetPoint;
    private float hitPoint;

    @Override
    public void added(Paddle entity) {
        super.added(entity);

        inviBall = new Ball();
    }

    @Override
    public void update(float delta) {

        if(getTargetX() >= GameConfig.WORLD_WIDTH / 4f){
            setInviBall();
            inviBall.update(delta);
            blockBallFromLeavingTheWorld();
            getTargetPoint();
            hitPoint();
            aiControl();
        } else {
            reset();
        }
    }

    // == private methods ==
    private void setInviBall(){
        if(!inviBallSettled){
            inviBallSettled = true;
            inviBall.setSize(entity.getTarget().getWidth(), entity.getTarget().getHeight());   // could be useful for reflections
            inviBall.setPosition(getTargetX(), getTargetY());
            inviBall.setVelocity(entity.getTarget().getAngleDeg(), GameConfig.BALL_START_SPEED * GameConfig.INVI_BALL_SPEED);
        }
    }

    private void getTargetPoint(){
        if(inviBall.getX() >= entity.getX() && !targetPointSettled){
            targetPointSettled = true;
            targetPoint = inviBall.getY();
        }
    }

    private float getTargetX(){
        return entity.getTarget().getX();
    }

    private float getTargetY(){
        return entity.getTarget().getY();
    }

    private void hitPoint(){
        if (!paddleHitSettled){
            paddleHitSettled = true;
            hitPoint = MathUtils.random((entity.getHeight() * 0.2f), (entity.getHeight() * 0.8f));
        }
    }

    private void reset(){
        paddleHitSettled = false;
        targetPointSettled = false;
        entity.setVelocityY(0);
        inviBallSettled = false;
        if(hit){
            hit = false;
        }
        stop = false;
    }

    private void aiControl() {
        entity.setVelocityY(0);

        if (!stop){
            if (targetPoint > (entity.getY() + hitPoint)  && !hit){
                if (getDist() < 0.1f) {
                    entity.setVelocityY(0);
                    return;
                }

                entity.setVelocityY(GameConfig.PADDLE_VELOCITY_Y);
            } else if (targetPoint < (entity.getY() + hitPoint) && !hit) {
                if (getDist() < 0.1f) {
                    entity.setVelocityY(0);
                    return;
                }

                entity.setVelocityY(-GameConfig.PADDLE_VELOCITY_Y);
            } else {
                entity.setVelocityY(0);
            }
        }

        if(Intersector.overlapConvexPolygons(entity.getBounds(), entity.getTarget().getBounds())){
            hit = true;
        }
    }

    private float getDist(){
        return Math.abs(targetPoint - (entity.getY() + hitPoint));
    }

    private void blockBallFromLeavingTheWorld(){

        // top
        float maxY = inviBall.getY() + inviBall.getHeight();
        if(maxY >= GameConfig.WORLD_HEIGHT){
            inviBall.setY(GameConfig.WORLD_HEIGHT - inviBall.getHeight());
            inviBall.multiplyVelocityY(-1);
        }

        // bottom
        if(inviBall.getY() <= 0){
            inviBall.setY(0);
            inviBall.multiplyVelocityY(-1);
        }

        if(entity.getTarget().getX() <= 0){
            reset();
        }

        if(entity.getTarget().getX() >= GameConfig.WORLD_WIDTH){
            reset();
        }
    }
}

