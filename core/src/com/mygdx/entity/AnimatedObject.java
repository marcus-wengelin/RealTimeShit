package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

public class AnimatedObject extends GameObject {

    private Animation<TextureRegion> anim;
    private float stateTime;

    public AnimatedObject(Animation<TextureRegion> anim, GridPoint2 origin) {
        super(anim.getKeyFrame(0), origin);
        this.anim = anim; 
        this.stateTime = 0;
    }

    @Override public void update(float dt) {
        this.stateTime += dt;
        this.tr = this.anim.getKeyFrame(this.stateTime);
    }
}
