package com.mygdx.input;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

import com.mygdx.game.WorldApi;

public class ZoomCameraCommand implements Command {
    private OrthographicCamera camera;
    private int scroll;
    public ZoomCameraCommand(OrthographicCamera camera, int scroll) {
        this.camera = camera;
        this.scroll = scroll;
    }

    @Override public boolean execute(WorldApi api) {
        float newZoom = camera.zoom;
        newZoom += this.scroll / 2.5f;
        newZoom = MathUtils.clamp(newZoom, 1, 2);
        camera.zoom = newZoom;
        return true;
    }
}
