package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.utils.TextRenderer;
import com.mygdx.utils.Constants;

public class MyGdxGame extends ApplicationAdapter {

    public SpriteBatch batch;

    private MyScreen screen;

    // everything here is in seconds
    private float   accumulator;
    private float   timeBetweenUpdates;
    private float   timeBetweenFrames;
    private float   maxTimeBetweenUpdates; // avoids spiral of death
    private float   frameLimiterTimer;
    private float   oneSecondTimer;
    private int     updateCountLastSecond;
    private int     frameCountLastSecond;
    private boolean printLoopInfo;

    /* Called when the Application is first created. */
    @Override public void create() {
        Gdx.app.setLogLevel(Constants.LOG_LEVEL);
        Gdx.app.log("MyGdxGame", "creating game");

        this.batch  = new SpriteBatch();
        TextRenderer.init();
        TextRenderer.setSpriteBatch(this.batch);
        TextRenderer.loadFonts("fonts");
        this.screen = new TestMapScreen(this);

        this.accumulator           = 0;
        this.timeBetweenUpdates    = 1/(float)Constants.UPDATES_PER_SECOND;
        this.timeBetweenFrames     = 1/(float)Constants.MAX_FRAMES_PER_SECOND;
        this.maxTimeBetweenUpdates = Constants.MAX_TIME_BETWEEN_UPDATES;
        this.frameLimiterTimer     = 0;
        this.oneSecondTimer        = 0;
        this.updateCountLastSecond = 0;
        this.frameCountLastSecond  = 0;
        this.printLoopInfo         = false;
    }

    /* Called when the Application should render itself. Updates and renders
       the current screen using a fixed timestep and interpolation. */
    @Override public void render() {
        float deltaTime = Math.min(Gdx.graphics.getRawDeltaTime(), this.maxTimeBetweenUpdates);
        this.oneSecondTimer    += deltaTime;
        this.accumulator       += deltaTime;
        this.frameLimiterTimer += deltaTime;

        while (this.accumulator >= this.timeBetweenUpdates) {
            this.screen.update(this.timeBetweenUpdates);
            this.accumulator -= this.timeBetweenUpdates;
            this.updateCountLastSecond++;
        }

        if (this.frameLimiterTimer >= this.timeBetweenFrames) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            this.screen.render(this.accumulator/this.timeBetweenUpdates);
            this.frameCountLastSecond++;
            this.frameLimiterTimer -= this.timeBetweenFrames;
        }

        if (this.oneSecondTimer >= 1) {
            if (this.printLoopInfo) {
                Gdx.app.log("MyGdxGame", String.format("%d UPS, %d FPS", this.updateCountLastSecond, this.frameCountLastSecond));
            }
            this.updateCountLastSecond = 0;
            this.frameCountLastSecond  = 0;
            this.oneSecondTimer        = 0;
        }
    }

    /* Called when the Application is paused, usually when it's not active or visible on screen. */
    @Override public void pause() {
        Gdx.app.log("MyGdxGame", "pausing game");
        this.screen.pause();
    }

    /* Called when the Application is resumed from a paused state, usually when it regains focus. */
    @Override public void resume() {
        Gdx.app.log("MyGdxGame", "resuming game");
        this.screen.resume();
    }

    /* Called when the Application is resized. */
    @Override public void resize(int width, int height) {
        Gdx.app.log("MyGdxGame", "the window was resized");
        this.screen.resize(width, height);
    }

    /* Called when the Application is destroyed. */
    @Override public void dispose() {
        Gdx.app.log("MyGdxGame", "tearing down game");
        this.screen.dispose();
        this.batch.dispose();
        TextRenderer.dispose();
    }

    public void setScreen(MyScreen screen) {
        Gdx.app.log("MyGdxGame", "entering new screen");
        this.screen.dispose();
        this.screen = screen;
    }
}
