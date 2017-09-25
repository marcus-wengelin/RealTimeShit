package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

public class GameCamera {

    public SpriteBatch               batch;
    public TiledMap                  map;
    public IsometricTiledMapRenderer mapRenderer;
    public OrthographicCamera        camera;
    public ScalingViewport           viewport;

    public GameCamera(SpriteBatch batch, String mapName, Scaling scalingMode) {
        this.map   = new TmxMapLoader().load("maps/"+mapName+"/"+mapName+".tmx");

        MapProperties prop = map.getProperties();
        int mapWidth  = prop.get("width",  Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        this.camera = new OrthographicCamera(mapWidth, mapHeight);

        this.batch = batch;
        this.batch.setProjectionMatrix(this.camera.combined);

        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.mapRenderer.setView(this.camera);

        this.viewport = new ScalingViewport(scalingMode, Gdx.graphics.getWidth(),
                                            Gdx.graphics.getHeight(), this.camera);
    }

    public void update() {
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.mapRenderer.setView(this.camera);
    }

    public void resizeViewport(int width, int height) {
        this.viewport.update(width, height, true);
        this.viewport.apply();
    }

    public void screenResize(int width, int height) {
        this.viewport.update(width, height, true);
        this.viewport.apply();
    }

    public Vector2 screenToWorld(Vector2 position) {
        return this.viewport.unproject(position);
    }

    public void dispose() {
        this.batch.dispose();
        this.map.dispose();
        this.mapRenderer.dispose();
    }

}
