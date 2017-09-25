package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

//@TODO: bad class name, doesn't reflect that it also stores and renders the map
public class GameCamera {

    public  TiledMap                  map;
    public  OrthographicCamera        camera;

    private IsometricTiledMapRenderer mapRenderer;
    private ScalingViewport           viewport;

    //@TODO: decide what arguments the constructor should take in order to be extensible but not overly verbose (much like this lengthy comment which should end soon)
    public GameCamera(String mapName, Scaling scalingMode) {
        this.map   = new TmxMapLoader().load("maps/"+mapName+"/"+mapName+".tmx");

        MapProperties prop = map.getProperties();
        int mapWidth  = prop.get("width",  Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        this.camera = new OrthographicCamera(mapWidth, mapHeight);

        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.mapRenderer.setView(this.camera);

        this.viewport = new ScalingViewport(scalingMode, Gdx.graphics.getWidth(),
                                            Gdx.graphics.getHeight(), this.camera);
    }

    public void update() {
        this.camera.update();
        this.mapRenderer.setView(this.camera);
    }

    public void renderMap() {
        this.mapRenderer.render();
    }

    public void move(Vector2 v) {
        this.camera.translate(v);
    }

    public void resizeViewport(int width, int height) {
        this.viewport.update(width, height, true);
        this.viewport.apply();
    }

    public Vector2 screenToWorld(Vector2 position) {
        return this.viewport.unproject(position);
    }

    public void dispose() {
        this.map.dispose();
        this.mapRenderer.dispose();
    }

}
