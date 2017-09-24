package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Hashtable;
import java.util.ArrayList;

public class AnimatedGo extends GameObject {
    private Hashtable<GoState, Animation<TextureRegion>> animTable;
    private float stateTime;

    public AnimatedGo(GridPoint2 origin, Hashtable<GoState, Animation<TextureRegion>> animTable,
                      ArrayList<GoState> allowedStates, GoState startingState) {
        super((TextureRegion)null, origin, allowedStates, startingState);
        this.animTable = animTable;
        this.stateTime = 0;

        /* @TODO: This may screw us if some state does not need an animation */
        for (GoState gs : allowedStates) {
            if (!this.animTable.containsKey(gs))
                throw new RuntimeException("Unmapped state");
        }

        this.tr = this.animTable.get(currentState).getKeyFrame(this.stateTime);
    }

    @Override public void update(float dt) {
        this.stateTime += dt;
        this.tr = this.animTable.get(currentState).getKeyFrame(this.stateTime);
    }
}
