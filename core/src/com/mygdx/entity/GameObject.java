package com.mygdx.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.utils.Constants;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

    public  GridPoint2 cell;
    private Vector2    position;
    private Texture    tex;

    public GameObject(Texture tex, GridPoint2 origin) {
        this.tex      = tex;
        this.cell     = origin.cpy();
        this.position = new Vector2(
            origin.x * Constants.TILE_WIDTH,
            origin.y * Constants.TILE_HEIGHT
        );
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch, float alpha) {
        float isoX = (this.cell.x + this.cell.y) * Constants.TILE_WIDTH/2f;
        float isoY = (this.cell.y - this.cell.x) * Constants.TILE_HEIGHT/2f;
        batch.draw(this.tex, isoX, isoY);
    }

}
