package com.mygdx.input;

import com.badlogic.gdx.math.GridPoint2;

import com.mygdx.entity.GameObject;
import com.mygdx.entity.MovableGo;

/**
 * Command for moving GameObjects
 */
public class MoveCommand implements Command {

    private GameObject go;
    private GridPoint2 dst;

    public MoveCommand(GameObject go, GridPoint2 dst) {
        this.go  = go;
        this.dst = dst;
    }

    @Override public boolean execute() {
        if (this.go instanceof MovableGo) {
            MovableGo mgo = (MovableGo)this.go;
            mgo.moveTo(dst);
            return true;
        }
        return false;
    }
}

