package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Interpolation;

import java.util.Hashtable;
import java.util.ArrayList;

import com.mygdx.utils.PathFinder;
import com.mygdx.utils.IsoMath;

public class MovableGo extends AnimatedGo {

    private Vector2               previousPosition;
    private ArrayList<GridPoint2> path;
    private float                 speed;
    private float                 timer;

    public MovableGo(GridPoint2 origin, Hashtable<GoState, Animation<TextureRegion>> animTable,
                     ArrayList<GoState> allowedStates, GoState startingState, float speed) {
        super(origin, animTable, allowedStates, startingState);
        this.previousPosition = this.position.cpy();
        this.path             = new ArrayList<GridPoint2>();
        this.speed            = speed;
        this.timer            = 0;
    }

    public ArrayList<GridPoint2> moveTo(GridPoint2 target) {
        this.path = PathFinder.aStarSearch(this.getCell(), target);
        this.timer = 0;
        return this.path;
    }

    @Override public void update(float dt) {
        if (path.size() > 1) {

            Vector2 origin = IsoMath.gridToWorld(path.get(0));
            Vector2 target = IsoMath.gridToWorld(path.get(1));
            this.timer += dt*speed*2f; // speed means the amount of units distance per second
            this.position = origin.interpolate(target, this.timer, Interpolation.linear);
            if (this.timer >= 1) {
                path.remove(0);
                this.position = target.cpy();
                this.timer = 0;
            }

            float xSpeed = this.previousPosition.x - this.position.x;
            Gdx.app.debug("MovableGo", ""+xSpeed);
            float ySpeed = this.previousPosition.y - this.position.y;
            if (Math.abs(xSpeed) >= Math.abs(ySpeed)) {
                if (xSpeed < 0) this.currentState = GoState.MOVING_E;
                else            this.currentState = GoState.MOVING_W;
            } else {
                if (ySpeed < 0) this.currentState = GoState.MOVING_N;
                else            this.currentState = GoState.MOVING_S;
            }
        }
        super.update(dt);
        this.previousPosition = this.position.cpy();
    }

    @Override public void render(SpriteBatch batch, float alpha) {
        super.render(batch, alpha); //@TODO: super bad! use linear interpolation
    }

}
