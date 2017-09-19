package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class InputHandler {

    private int scroll;
    private int mouseX;
    private int mouseY;

    public InputHandler() {
        this.scroll = 0;
        Gdx.input.setInputProcessor(new MyInputProcesser());
    }

    public int scroll() {
        return this.scroll;
    }

    public void resetInputs() {
        this.scroll = 0;
    }

    private class MyInputProcesser extends InputAdapter {
        public boolean scrolled(int amount) {
            scroll = amount;
            return true;
        }

        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            mouseX = screenX;
            mouseY = screenY;
            return true;
        }
    }

}
