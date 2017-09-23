package com.mygdx.utils;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class Mathy {

    private Mathy() {}

    public static Vector2 cellToVector(GridPoint2 cell) {
        return new Vector2(cell.x * Constants.TILE_WIDTH, cell.y * Constants.TILE_HEIGHT);
    }

    public static GridPoint2 vectorToCell(Vector2 vector) {
        return new GridPoint2(
            Math.round(vector.x / Constants.TILE_WIDTH),
            Math.round(vector.y / Constants.TILE_HEIGHT)
        );
    }

    public static Vector2 cellToIso(GridPoint2 cell) {
        return new Vector2(
            (cell.x + cell.y) * Constants.TILE_WIDTH/2f,
            (cell.y - cell.x) * Constants.TILE_HEIGHT/2f
        );
    }

    public static Vector2 worldToIso(Vector2 position) {
        return new Vector2(
            (position.x + position.y)/2f,
            (position.y - position.x)/2f
        );
    }

}
