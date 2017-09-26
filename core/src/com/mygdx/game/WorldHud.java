package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.utils.TextRenderer;
import com.mygdx.utils.TextRenderer.Alignment;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class WorldHud {

    private SpriteBatch        batch;
    private OrthographicCamera camera;
    private ScalingViewport    viewport;

    public WorldHud(SpriteBatch batch) {
        this.batch    = batch;

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),
                                            Gdx.graphics.getHeight());

        this.viewport = new ScalingViewport(Scaling.none, Gdx.graphics.getWidth(),
                                            Gdx.graphics.getHeight());
    }

    public void render() {
        Gdx.app.debug("WorldHud", "drawing hud...");
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        assert TextRenderer.draw(this.batch, "fipps_modified", "hi!", 0, 0, Alignment.TOP_RIGHT);
        //assert TextRenderer.drawOnWorld(batch, "fipps_modified", "hi!", -150, -150, Alignment.CENTER);
        //assert TextRenderer.drawOnWorld(batch, "fipps_modified", "i'm here", 500, 0, Alignment.TOP_RIGHT);
        //assert TextRenderer.drawOnScreen(batch, "fipps_modified", "--- HUD ---", 0.5f, 0.95f, Alignment.BOTTOM);
        //assert TextRenderer.drawOnScreen(batch, "fipps_modified", "*", 1, 0, Alignment.TOP_LEFT);
        this.batch.end();
    }

    public void resizeViewport(int width, int height) {
        this.viewport.update(width, height);
        this.viewport.apply();
    }

}
