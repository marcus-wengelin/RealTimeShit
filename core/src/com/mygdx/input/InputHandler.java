package com.mygdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.GridPoint2;

import com.mygdx.entity.GameObject;
import com.mygdx.game.WorldApi;

import java.util.ArrayList;

public class InputHandler {

    private WorldApi api;

    public InputHandler(WorldApi api) {
        this.api = api;
        Gdx.input.setInputProcessor(new MyInputProcessor());
    }

    private class MyInputProcessor extends InputAdapter {
        Vector2 lastDragPos = Vector2.Zero;
        @Override public boolean scrolled(int amount) {
            new ZoomCameraCommand(api.getOrthoCamera(), amount).execute(api);
            return true;
        }

        @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (Gdx.input.isButtonPressed(0)) {
                new MoveCameraCommand(api.getOrthoCamera(),
                    Gdx.input.getDeltaX(), Gdx.input.getDeltaY()
                ).execute(api);
            } else return false;
            return true;
        }

        @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (button == 2) {
                new SelectUnitCommand(screenX, screenY, (int)this.lastDragPos.x, (int)this.lastDragPos.y).execute(api);
                this.lastDragPos.x = 0;
                this.lastDragPos.y = 0;
            }
            return true;
        }

        @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            GridPoint2 cell = api.mouseToGrid(screenX, screenY);
            if (button == 1) {
                /* cell == null if outside of map */
                if (cell != null) {
                    /* @TODO: This logic should probably be inside the MoveGo command */
                    ArrayList<GameObject> units = api.getSelectedUnits();
                    for (GameObject go : units)
                        new MoveGoCommand(go, cell).execute(api);
                } else return false;
            } else if (button == 2) {
                this.lastDragPos.x = screenX;
                this.lastDragPos.y = screenY;
            }
            return true;
        }
    }
}
