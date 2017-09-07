package com.fcc.pong.entity;

import com.fcc.util.entity.EntityBase;
import com.fcc.util.shape.ShapeUtils;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class Ball extends EntityBase{

    public Ball() {
    }

    // == protected methods ==
    @Override
    protected float[] createVertices() {
        return ShapeUtils.createPolygon(width/2f, height/2f, width/2f, 30);
    }
}
