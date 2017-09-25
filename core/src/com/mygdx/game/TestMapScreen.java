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

import com.mygdx.utils.*;
import com.mygdx.utils.TextRenderer.Alignment;
import com.mygdx.entity.*;

import java.util.ArrayList;

public class TestMapScreen extends MyScreen {

    private GameCamera                gameCamera;
    private InputHandler              input;
	private MovableGo                 player;
    private GameObject                marker;
    private ArrayList<GameObject>     pathMarkers;

    public TestMapScreen(MyGdxGame game) {
        super(game);

        this.input       = new InputHandler();
        this.player      = GoFactory.makePlayer(0, 0);
        this.marker      = GoFactory.makeMarker(0, 0);
        this.pathMarkers = new ArrayList<GameObject>();

        this.gameCamera  = new GameCamera("test", Scaling.none);
        TextRenderer.setCamera(this.gameCamera.camera);
        PathFinder.setMap(this.gameCamera.map);
    }

    @Override public void update(float deltaTime) {
        if (!this.input.mouseDrag.isZero() && Gdx.input.isButtonPressed(0)) {
            this.gameCamera.move(new Vector2(-this.input.mouseDrag.x, this.input.mouseDrag.y));
        }

        //@TODO: scrolling is broken
        if (this.input.scroll != 0) {
            int viewportWidth  = Gdx.graphics.getWidth() +this.input.scroll*10;
            int viewportHeight = Gdx.graphics.getHeight()+this.input.scroll*10;
            this.gameCamera.resizeViewport(viewportWidth, viewportHeight);
        }
        this.gameCamera.update();
        this.game.batch.setProjectionMatrix(this.gameCamera.camera.combined);

        if (Gdx.input.isButtonPressed(0)) {
            Vector2 worldCoords = this.gameCamera.screenToWorld(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            marker.setCell(IsoMath.orthoWorldToIso(worldCoords));
            ArrayList<GridPoint2> pathCells = this.player.moveTo(marker.getCell());
            pathMarkers.clear();
            for (GridPoint2 n : pathCells) pathMarkers.add(GoFactory.makeMarker(n.x, n.y));
        }
        this.player.update(deltaTime);

        this.input.resetInputs();
    }

    @Override public void render(float alpha) {
        this.gameCamera.renderMap();
        SpriteBatch batch = this.game.batch;
        batch.begin();
        this.marker.render(batch, alpha);
        for (GameObject go : pathMarkers) go.render(batch, alpha);
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

}
