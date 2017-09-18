package com.mygdx.game;

abstract public class MyScreen {

    protected MyGdxGame game;

    public MyScreen(MyGdxGame game) {
        this.game = game;
    }

    public abstract void update(float dt);
    public abstract void render(float a);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();

}
