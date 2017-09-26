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
import com.mygdx.utils.Constants;

public class MovableGo extends AnimatedGo {

    private Vector2               previousPosition;
    private ArrayList<GridPoint2> path;
    private float                 speed; // units per second (a cell is 1x1)

    public MovableGo(GridPoint2 origin, Hashtable<GoState, Animation<TextureRegion>> animTable,
                     ArrayList<GoState> allowedStates, GoState startingState, float speed) {
        super(origin, animTable, allowedStates, startingState);
        this.previousPosition = this.position.cpy();
        this.path             = new ArrayList<GridPoint2>();
        this.speed            = speed*Constants.TILE_WIDTH;
    }

    public ArrayList<GridPoint2> moveTo(GridPoint2 target) {
        GridPoint2 nearestCell = null;
        if (!this.path.isEmpty()) nearestCell = this.path.get(0);

        this.path = PathFinder.aStarSearch(this.getCell(), target);

        if (this.path.isEmpty()) {
            // if the go was in the middle of a travel, finish it
            if (nearestCell != null) this.path.add(nearestCell);
        } else if (this.path.size() > 1) {
            this.path.remove(0);
        }

        return this.path;
    }

    //@TODO: fix the final animation flipping issue
    @Override public void update(float dt) {
        if (!this.path.isEmpty()) {

            Vector2 target    = IsoMath.gridToWorld(this.path.get(0));
            boolean moveHoriz = IsoMath.isEastOf(target,  this.position) ||
                                IsoMath.isWestOf(target,  this.position);
            boolean moveVert  = IsoMath.isNorthOf(target, this.position) ||
                                IsoMath.isSouthOf(target, this.position);
            if (this.position.equals(target)) {
                this.path.remove(0);
            } else if (moveHoriz && moveVert) {
                float diagSpeed = 1f/(float)Math.sqrt(2);
                this.position.x = moveTowards(this.position.x, target.x, speed*diagSpeed*dt);
                this.position.y = moveTowards(this.position.y, target.y, speed*diagSpeed*dt);
            } else if (moveHoriz) {
                this.position.x = moveTowards(this.position.x, target.x, this.speed*dt);
                this.currentState = IsoMath.isWestOf(target, this.position) ?
                                    GoState.MOVING_E : GoState.MOVING_W;
            } else if (moveVert) {
                this.position.y = moveTowards(this.position.y, target.y, this.speed*dt);
                this.currentState = IsoMath.isSouthOf(target, this.position) ?
                                    GoState.MOVING_N : GoState.MOVING_S;
            }
        }
        super.update(dt);
        this.previousPosition = this.position.cpy();
    }

    @Override public void render(SpriteBatch batch, float alpha) {
        Vector2 oldPosition = this.previousPosition.cpy();
        this.previousPosition.lerp(this.position, alpha);
        super.render(batch, alpha); //@TODO: super bad! use linear interpolation
        this.previousPosition = oldPosition;
    }

    private float moveTowards(float origin, float goal, float value) {
        if (origin > goal) {
            origin -= value;
            if (origin < goal) origin = goal;
        } else {
            origin += value;
            if (origin > goal) origin = goal;
        }
        return origin;
    }
}
