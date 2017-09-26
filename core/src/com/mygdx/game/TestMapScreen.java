package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;

import com.mygdx.utils.*;
import com.mygdx.utils.TextRenderer.Alignment;
import com.mygdx.entity.*;
import com.mygdx.input.InputHandler;

import java.util.ArrayList;

public class TestMapScreen extends MyScreen implements WorldApi {

    private GameCamera            gameCamera;
    private InputHandler          input;
	private MovableGo             player;
    private GameObject            marker;

    public TestMapScreen(MyGdxGame game) {
        super(game);

        this.input       = new InputHandler((WorldApi)this);
        this.player      = GoFactory.makePlayer(0, 0);
        this.marker      = GoFactory.makeMarker(0, 0);

        this.gameCamera  = new GameCamera("test", Scaling.fit);
        TextRenderer.setCamera(this.gameCamera.camera);
        PathFinder.setMap(this.gameCamera.map);
    }

    @Override public void update(float deltaTime) {
        this.gameCamera.update();
        this.game.batch.setProjectionMatrix(this.gameCamera.camera.combined);
        this.player.update(deltaTime);
        this.input.resetInputs();
    }

    @Override public void render(float alpha) {
        this.gameCamera.renderMap();
        SpriteBatch batch = this.game.batch;
        batch.begin();
        this.marker.render(batch, alpha);
        this.player.render(batch, alpha);
        assert TextRenderer.drawOnWorld("fipps_modified", "hi!", -150, -150, Alignment.CENTER);
        assert TextRenderer.drawOnWorld("fipps_modified", "i'm here", 500, 0, Alignment.TOP_RIGHT);
        //assert TextRenderer.drawOnScreen("fipps_modified", "--- HUD ---", 0.5f, 0.95f, Alignment.BOTTOM);
        //assert TextRenderer.drawOnScreen("fipps_modified", "*", 1, 0, Alignment.TOP_LEFT);
        batch.end();
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void resize(int width, int height) {
        this.gameCamera.resizeViewport(width, height);
    }

    @Override public void dispose() {
        this.gameCamera.dispose();
    }

    /* API CODE - BEWARE */

    @Override public GameObject getSelectedUnit() {
        return this.player;
    }

    @Override public GridPoint2 mouseToGrid(int x, int y) {
        Vector2 wcoords = this.gameCamera.screenToWorld(
            new Vector2(x, y)
        );
        GridPoint2 cell = IsoMath.orthoWorldToIso(wcoords);
        marker.setCell(cell); // @TODO: only here for dbg atm
        return cell;
    }

    @Override public OrthographicCamera getOrthoCamera() {
        return this.gameCamera.camera;

    }
}
