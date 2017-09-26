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

    private InputHandler              input; // @TODO: move into MyGdxGame

    private TiledMap                  map;
    private Vector2                   worldSize;
    private OrthographicCamera        camera;
    private IsometricTiledMapRenderer mapRenderer;
    private ScalingViewport           viewport;

	private MovableGo                 player;
    private GameObject                marker;

    public TestMapScreen(MyGdxGame game) {
        super(game);
        this.input       = new InputHandler((WorldApi)this);

        this.map   = new TmxMapLoader().load("maps/test/test.tmx");

        MapProperties prop = map.getProperties();
        this.worldSize = new Vector2(
            prop.get("width",  Integer.class),
            prop.get("height", Integer.class)
        );
        this.camera = new OrthographicCamera(this.worldSize.x, this.worldSize.y);

        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.mapRenderer.setView(this.camera);

        this.viewport = new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(),
                                            Gdx.graphics.getHeight(), this.camera);
        TextRenderer.setCamera(this.camera);
        PathFinder.setMap(this.map);

        this.player      = GoFactory.makePlayer(0, 0);
        this.marker      = GoFactory.makeMarker(0, 0);
    }

    @Override public void update(float deltaTime) {
        this.camera.update();
        this.mapRenderer.setView(this.camera);
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.player.update(deltaTime);
        this.input.resetInputs();
    }

    @Override public void render(float alpha) {
        this.mapRenderer.render();
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
        this.viewport.update(width, height);
        this.viewport.apply();
    }

    @Override public void dispose() {
        this.map.dispose();
        this.mapRenderer.dispose();
    }

    /* API CODE - BEWARE */

    @Override public GameObject getSelectedUnit() {
        return this.player;
    }

    @Override public GridPoint2 mouseToGrid(int x, int y) {
        Vector2 wcoords = this.viewport.unproject(
            new Vector2(x, y)
        );
        GridPoint2 cell = IsoMath.orthoWorldToIso(wcoords);
        marker.setCell(cell); // @TODO: only here for dbg atm
        return cell;
    }

    @Override public OrthographicCamera getOrthoCamera() {
        return this.camera;

    }
}
