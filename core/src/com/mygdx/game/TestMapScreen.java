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
    private WorldHud                  hud;

	private MovableGo                 player;
    private GameObject                marker;

    private ArrayList<GameObject>     selectedUnits;

    public TestMapScreen(MyGdxGame game) {
        super(game);
        this.input = new InputHandler((WorldApi)this);

        this.map = new TmxMapLoader().load("maps/test/test.tmx");
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
        PathFinder.setMap(this.map);

        this.hud = new WorldHud(this.game.batch);

        this.player = GoFactory.makePlayer(0, 0);
        this.marker = GoFactory.makeMarker(0, 0);

        this.selectedUnits = new ArrayList<GameObject>();
    }

    @Override public void update(float deltaTime) {
        this.player.update(deltaTime);
        this.camera.update();
    }

    @Override public void render(float alpha) {
        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();
        SpriteBatch batch = this.game.batch;
        batch.setProjectionMatrix(this.camera.combined);
        batch.begin();
        this.marker.render(batch, alpha);
        for (GameObject unit : this.selectedUnits) {
            GoFactory.makeSelectedMarker(unit.getCell().x, unit.getCell().y).render(batch, alpha);
        }
        this.player.render(batch, alpha);
        batch.end();
        this.hud.render();
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

    @Override public ArrayList<GameObject> getUnitsInCell(GridPoint2 cell) {
        // @TODO: Pretty useless atm since it only checks player
        ArrayList<GameObject> l = new ArrayList<GameObject>();
        if (this.player.getCell().equals(cell))
            l.add(this.player);
        return l;
    }

    @Override public void setSelectedUnits(ArrayList<GameObject> selectedUnits) {
        Gdx.app.debug("TestMapScreen", "Setting selected units ("+selectedUnits.size()+")");
        this.selectedUnits = selectedUnits;
    }

    @Override public ArrayList<GameObject> getSelectedUnits() {
        return this.selectedUnits;
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
