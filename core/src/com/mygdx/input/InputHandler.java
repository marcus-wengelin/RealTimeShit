package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class InputHandler {

    public int     scroll;
    public Vector2 mouseDrag;

    public InputHandler() {
        this.scroll    = 0;
        this.mouseDrag = Vector2.Zero;
        Gdx.input.setInputProcessor(new MyInputProcessor());
    }

    public void resetInputs() {
        this.scroll = 0;
        this.mouseDrag.setZero();
    }

    private class MyInputProcessor extends InputAdapter {
        @Override public boolean scrolled(int amount) {
            scroll += amount;
            return true;
        }

        @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
            mouseDrag.x += Gdx.input.getDeltaX();
            mouseDrag.y += Gdx.input.getDeltaY();
            return true;
        }
    }

}
