package com.mygdx.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.utils.IsoMath;
import com.mygdx.utils.Constants;

import java.util.ArrayList;

public class GameObject {
    public GoState currentState;

    protected ArrayList<GoState> allowedStates;
    protected Vector2 position;
    protected TextureRegion tr;

    public GameObject(Texture tex, GridPoint2 origin, ArrayList<GoState> allowedStates, GoState startingState) {
        this(new TextureRegion(tex), origin, allowedStates, startingState);
    }

    public GameObject(TextureRegion tr, GridPoint2 origin, ArrayList<GoState> allowedStates, GoState startingState) {
        assert !allowedStates.isEmpty();
        this.currentState  = startingState;
        this.tr            = tr;
        this.position      = IsoMath.gridToWorld(origin);
        this.allowedStates = allowedStates;
    }

    public GridPoint2 getCell() {
        return IsoMath.worldToGrid(this.position);
    }

    public void setCell(GridPoint2 cell) {
        this.position = IsoMath.gridToWorld(cell);
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch, float alpha) {
        Vector2    drawPos = IsoMath.worldToScreen(this.position);
        batch.draw(this.tr, drawPos.x, drawPos.y);
    }

}
