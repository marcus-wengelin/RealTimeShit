package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Hashtable;
import java.util.ArrayList;

import com.mygdx.utils.PathFinder;
import com.mygdx.utils.IsoMath;

public class MovableGo extends AnimatedGo {

    private ArrayList<GridPoint2> path;
    private float timer;
    private float speed;

    public MovableGo(GridPoint2 origin, Hashtable<GoState, Animation<TextureRegion>> animTable,
                     ArrayList<GoState> allowedStates, GoState startingState, float speed ) {
        super(origin, animTable, allowedStates, startingState);
        this.speed = speed;
    }

    public void moveTo(GridPoint2 target) {
        path = PathFinder.aStarSearch(target);
    }

    @Override public void update(float dt) {
        /* movement logic */
        GridPoint2 cell = this.getCell();
        if (IsoMath.isEastOf(cell, path.get(0))) {
            this.currentState = GoState.MOVING_E;
        } else if (IsoMath.isWestOf(cell, path.get(0))) {
            this.currentState = GoState.MOVING_W;
        } else if (IsoMath.isSouthOf(cell, path.get(0))) {
            this.currentState = GoState.MOVING_S;
        } else if (IsoMath.isNorthOf(cell, path.get(0))) {
            this.currentState = GoState.MOVING_N;
        } else {
            path.remove(0);
            /* 
                @TODO: code below
                this.currentState = GoState.IDLE;
            */
        } 
        this.position.lerp(timer*speed);
        super.update(dt);
    }

}
