package com.mygdx.input;

import com.badlogic.gdx.math.GridPoint2;

import com.mygdx.game.WorldApi;
import com.mygdx.entity.GameObject;
import com.mygdx.entity.MovableGo;

/**
 * Command for moving GameObjects
 */
public class MoveGoCommand implements Command {

    private GameObject go;
    private GridPoint2 dst;

    public MoveGoCommand(GameObject go, GridPoint2 dst) {
        this.go  = go;
        this.dst = dst;
    }

    @Override public boolean execute(WorldApi api) {
        if (this.go instanceof MovableGo) {
            ((MovableGo) this.go).moveTo(dst);
            return true;
        }
        return false;
    }
}
