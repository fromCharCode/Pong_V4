package com.fcc.pong.entity;

import com.fcc.util.entity.EntityBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class Paddle extends EntityBase{

    // == attributes ==
    private Ball ball;

    // == constructors ==
    Paddle() {
    }

    // == public methods ==
    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Ball getTarget(){
        return ball;
    }
}
