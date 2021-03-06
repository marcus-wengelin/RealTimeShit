package com.mygdx.input;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.mygdx.game.WorldApi;

public class MoveCameraCommand implements Command {

    private OrthographicCamera camera;
    private float x,y;
    public MoveCameraCommand(OrthographicCamera camera, float x, float y) {
        this.camera = camera;
        this.x = x;
        this.y = y;
    }

    // @TODO: doesn't work properly when stretching out the window
    @Override public boolean execute() {
        float nx = -this.x * camera.zoom;
        float ny =  this.y * camera.zoom;
        this.camera.translate(nx, ny);
        this.camera.update();
        return true;
    }

}
