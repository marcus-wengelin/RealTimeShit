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
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

import com.mygdx.utils.TextRenderer;
import com.mygdx.utils.PathFinder;
import com.mygdx.utils.PathFinder.Node;
import com.mygdx.utils.TextRenderer.Alignment;

import java.util.ArrayList;

public class TestMapScreen extends MyScreen {

    private TiledMap                  map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera        camera;
    private InputHandler              input;
	private GameObject                player;
    private GameObject                marker;
    private ArrayList<GameObject>     pathMarkers;

    public TestMapScreen(MyGdxGame game) {
        super(game);
        Parameters params = new Parameters();
        this.map         = new TmxMapLoader().load("maps/test/test.tmx", params);
        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.camera      = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mapRenderer.setView(this.camera);
        this.input = new InputHandler();
        this.player = new GameObject(new Texture("sprites/jack.png"), 0, 0);
        this.marker = new GameObject(new Texture("sprites/marker.png"), 1, 14);
        this.pathMarkers = new ArrayList<GameObject>();
        TextRenderer.setCamera(this.camera);
        PathFinder.setMap(this.map);
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
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.mapRenderer.setView(this.camera);

        if ( Gdx.input.isButtonPressed(0) ) {
            Vector3 worldCoordinates = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            marker.yTile = (int)((worldCoordinates.x/32f + worldCoordinates.y/16f) / 2f - .5);
            marker.xTile = (int)((-worldCoordinates.y/16f + worldCoordinates.x/32f) / 2f + .5);
            ArrayList<Node> pathNodes = PathFinder.aStarSearch(player.xTile, player.yTile, marker.xTile, marker.yTile);
            pathMarkers.clear();
            for (Node n : pathNodes)
                pathMarkers.add(new GameObject(new Texture("sprites/marker.png"), n.x, n.y));
        }

        this.input.resetInputs();
    }

    public void render(float a) {
        this.mapRenderer.render();
        this.game.batch.begin();
        this.player.render(this.game.batch);
        this.marker.render(this.game.batch);
        for (GameObject go : pathMarkers) go.render(this.game.batch);
        assert TextRenderer.drawOnWorld("fipps_modified", "hi!", -150, -150, Alignment.CENTER);
        assert TextRenderer.drawOnWorld("fipps_modified", "i'm here", 500, 0, Alignment.TOP_RIGHT);
        assert TextRenderer.drawOnScreen("fipps_modified", "--- HUD ---", 0.5f, 0.95f, Alignment.BOTTOM);
        assert TextRenderer.drawOnScreen("fipps_modified", "*", 1, 0, Alignment.TOP_LEFT);
        this.game.batch.end();
    }

    public void pause() {}
    public void resume() {}

    public void dispose() {
        this.map.dispose();
        this.mapRenderer.dispose();
    }
}
