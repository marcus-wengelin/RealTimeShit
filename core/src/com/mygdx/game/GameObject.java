package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObject {

    private Texture tex;

    private int xTile;
    private int yTile;
    private float xCoord;
    private float yCoord;

    public GameObject(Texture tex, int xTile, int yTile) {
        this.tex = tex;
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public GameObject(Texture tex) {
        this.tex = tex;
    }

    public void update(float dt) {
    }

    public void render(SpriteBatch batch) {
        float isoX = (this.xTile + this.yTile) * 32;
        float isoY = (this.yTile - this.xTile) * 16;
        batch.draw(this.tex, isoX, isoY);
    }
}
