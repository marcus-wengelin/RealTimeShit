package com.mygdx.game;

abstract public class MyScreen {

    protected MyGdxGame game;

    public MyScreen(MyGdxGame game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);
    public abstract void render(float alpha);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();

}
