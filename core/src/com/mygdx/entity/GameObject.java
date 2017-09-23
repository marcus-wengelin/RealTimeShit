package com.mygdx.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.utils.Constants;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import com.mygdx.utils.Mathy;

public class GameObject {

    private Vector2 position;
    private Texture tex;

    public GameObject(Texture tex, GridPoint2 origin) {
        this.tex      = tex;
        this.position = Mathy.cellToVector(origin);
    }

    public GridPoint2 getCell() {
        return Mathy.vectorToCell(this.position);
    }

    public void setCell(GridPoint2 cell) {
        this.position = Mathy.cellToVector(cell);
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch, float alpha) {
        //@TODO: investigate why the commented line doesn't work
        GridPoint2 cell    = this.getCell();
        Vector2    drawPos = Mathy.cellToIso(Mathy.vectorToCell(this.position));
        //Vector2    drawPos = Mathy.worldToIso(this.position);
        batch.draw(this.tex, drawPos.x, drawPos.y);
    }

}
