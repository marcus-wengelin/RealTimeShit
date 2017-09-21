package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

import com.mygdx.utils.TextRenderer;
import com.mygdx.utils.TextRenderer.Alignment;

public class TestMapScreen extends MyScreen {

    private TiledMap                  map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera        camera;
    private InputHandler              input;
	private GameObject                player;

    public TestMapScreen(MyGdxGame game) {
        super(game);
        Parameters params = new Parameters();
        this.map         = new TmxMapLoader().load("maps/test/test.tmx", params);
        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.camera      = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mapRenderer.setView(this.camera);
        this.input = new InputHandler();
        this.player = new GameObject(new Texture("sprites/jack.png"), 1, 14);
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

        /*
        map.x = (screen.x/tileWidthHalf + screen.y/tileHeightHalf) / 2
        map.y = (screen.y/tileHeightHalf - screen.x/tileWidthHalf) / 2
        */
        int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
        int mX = Gdx.input.getX();

        System.out.println("TileX: "+((mX/32 + mY/16)));
        System.out.println("TileY: "+((mY/16 - mX/32)));

        this.input.resetInputs();
    }

    public void render(float a) {
        this.mapRenderer.render();
        SpriteBatch batch = (SpriteBatch) this.mapRenderer.getBatch();
        batch.begin();
        TextRenderer.draw("fipps_modified", "debug", 0, 0, Alignment.TOP_RIGHT);
        this.player.render(batch);
        batch.end();
    }

    public void pause() {}
    public void resume() {}

    public void dispose() {
        this.map.dispose();
        this.mapRenderer.dispose();
    }
}
