package com.mygdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.WorldApi;

public class InputHandler {

    public int     scroll;
    public Vector2 mouseDrag;

    private WorldApi api;
    private int prevX, prevY;

    public InputHandler(WorldApi api) {
        this.scroll    = 0;
        this.mouseDrag = Vector2.Zero;
        this.api       = api;

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
            new MoveCameraCommand(api.getOrthoCamera(),
                 Gdx.input.getDeltaX(), Gdx.input.getDeltaY()
            ).execute();
            return true;
        }

        @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (button != 1) return false;
            new MoveGoCommand(api.getSelectedUnit(), api.mouseToGrid(screenX, screenY)).execute();
            return true;
        }
    }

}
