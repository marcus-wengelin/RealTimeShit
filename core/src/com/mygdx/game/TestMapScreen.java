package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;

public class TestMapScreen extends MyScreen {

    private TiledMap                  map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera        camera;
    private InputHandler              input;

    public TestMapScreen(MyGdxGame game) {
        super(game);
        this.map         = new TmxMapLoader().load("maps/test/test.tmx");
        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.camera      = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mapRenderer.setView(this.camera);
        this.input = new InputHandler();
    }

    public void update(float dt) {
        float dx       = 0;
        float dy       = 0;
        float camSpeed = 5;

        if (Gdx.input.isKeyPressed(Keys.W)) dy += camSpeed/2f;
        if (Gdx.input.isKeyPressed(Keys.A)) dx -= camSpeed;
        if (Gdx.input.isKeyPressed(Keys.S)) dy -= camSpeed/2f;
        if (Gdx.input.isKeyPressed(Keys.D)) dx += camSpeed;

        this.camera.translate(dx, dy);
        this.camera.zoom += this.input.scroll()*0.2f;
        this.camera.update();
        this.mapRenderer.setView(this.camera);

        this.input.resetInputs();
    }

    public void render(float a) {
        this.mapRenderer.render();
    }

    public void pause() {}
    public void resume() {}

    public void dispose() {
        this.map.dispose();
        this.mapRenderer.dispose();
    }

}
