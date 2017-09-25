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

import com.mygdx.utils.*;
import com.mygdx.utils.TextRenderer.Alignment;
import com.mygdx.entity.*;

import java.util.ArrayList;

public class TestMapScreen extends MyScreen {

    private TiledMap                  map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera        camera;
    private InputHandler              input;
	private MovableGo                 player;
    private GameObject                marker;
    private ArrayList<GameObject>     pathMarkers;

    public TestMapScreen(MyGdxGame game) {
        super(game);
        this.map         = new TmxMapLoader().load("maps/test/test.tmx");
        this.mapRenderer = new IsometricTiledMapRenderer(this.map);
        this.camera      = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mapRenderer.setView(this.camera);
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.input = new InputHandler();
        this.player = GoFactory.makePlayer(0, 0);
        this.marker = GoFactory.makeMarker(0, 0);
        this.pathMarkers = new ArrayList<GameObject>();
        TextRenderer.setCamera(this.camera);
        PathFinder.setMap(this.map);
    }

    @Override public void update(float deltaTime) {
        if (!this.input.mouseDrag.isZero()) {
            this.camera.translate(-this.input.mouseDrag.x*10, this.input.mouseDrag.y*10);
            Gdx.app.debug("TestMapScreen", "detected mouse drag "+this.input.mouseDrag);
        }

/*        if (Gdx.input.isKeyPressed(Keys.W)) dy += camSpeed/2f;
        if (Gdx.input.isKeyPressed(Keys.A)) dx -= camSpeed;
        if (Gdx.input.isKeyPressed(Keys.S)) dy -= camSpeed/2f;
        if (Gdx.input.isKeyPressed(Keys.D)) dx += camSpeed;

        if (dx != 0 || dy != 0)*/

        if (this.input.scroll != 0) {
            int viewportWidth  = Gdx.graphics.getWidth() *this.input.scroll;
            int viewportHeight = Gdx.graphics.getHeight()*this.input.scroll;
            this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        }

        this.camera.update();
        this.player.update(deltaTime);
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.mapRenderer.setView(this.camera);

        if (Gdx.input.isButtonPressed(0)) {
            Vector3 worldCoords = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            marker.setCell(IsoMath.orthoWorldToIso(worldCoords));
            ArrayList<GridPoint2> pathCells = this.player.moveTo(marker.getCell());
            pathMarkers.clear();
            for (GridPoint2 n : pathCells) pathMarkers.add(GoFactory.makeMarker(n.x, n.y));
        }

        this.input.resetInputs();
    }

    @Override public void render(float alpha) {
        this.mapRenderer.render();
        this.game.batch.begin();
        this.marker.render(this.game.batch, alpha);
        for (GameObject go : pathMarkers) go.render(this.game.batch, alpha);
        this.player.render(this.game.batch, alpha);
        assert TextRenderer.drawOnWorld("fipps_modified", "hi!", -150, -150, Alignment.CENTER);
        assert TextRenderer.drawOnWorld("fipps_modified", "i'm here", 500, 0, Alignment.TOP_RIGHT);
        //assert TextRenderer.drawOnScreen("fipps_modified", "--- HUD ---", 0.5f, 0.95f, Alignment.BOTTOM);
        //assert TextRenderer.drawOnScreen("fipps_modified", "*", 1, 0, Alignment.TOP_LEFT);
        this.game.batch.end();
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void resize(int width, int height) {
        this.camera = new OrthographicCamera(width, height);
    }

    @Override public void dispose() {
        this.map.dispose();
        this.mapRenderer.dispose();
    }
}
