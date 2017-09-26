package com.mygdx.input;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class ZoomCameraCommand implements Command {
    private OrthographicCamera camera;
    private int scroll;
    public ZoomCameraCommand(OrthographicCamera camera, int scroll) {
        this.camera = camera;
        this.scroll = scroll;
    }

    @Override public boolean execute() {
        float newZoom = camera.zoom;
        newZoom += this.scroll / 2.5f;
        newZoom = MathUtils.clamp(newZoom, 1, 2);
        camera.zoom = newZoom;
        return true;
    }
}
