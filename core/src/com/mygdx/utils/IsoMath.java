package com.mygdx.utils;

import static com.mygdx.utils.Constants.TILE_WIDTH;
import static com.mygdx.utils.Constants.TILE_HEIGHT;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class IsoMath {

    private IsoMath() {}

    /**
     * Converts grid coordinates to world coordinates
     *
     * @param GridPoint2    grid coordinates
     * @return Vector2      world coordinates
     */
    public static Vector2 gridToWorld(GridPoint2 cell) {
        return new Vector2(
            cell.x * TILE_WIDTH,
            cell.y * TILE_HEIGHT
        );
    }

    /**
     * Converts world coordinates to grid coordinates
     *
     * @param Vector2       world coordinates
     * @return GridPoint2   grid coordinates
     */
    public static GridPoint2 worldToGrid(Vector2 vector) {
        return new GridPoint2(
            Math.round(vector.x / TILE_WIDTH),
            Math.round(vector.y / TILE_HEIGHT)
        );
    }

    /**
     * Converts grid coordinates to screen coordinates
     *
     * @param GridPoint2    grid coordinates
     * @return Vector2      screen coordinates
     */
    public static Vector2 gridToScreen(GridPoint2 cell) {
        return new Vector2(
            (cell.x + cell.y) * TILE_WIDTH/2f,
            (cell.y - cell.x) * TILE_HEIGHT/2f
        );
    }

   /**
    * Converts world coordinates to screen coordinates
    *
    * @param Vector2    world coordinates
    * @return Vector2   screen coordinates
    *
    */ 
    public static Vector2 worldToScreen(Vector2 position) {
        return new Vector2(
            (position.x + position.y*TILE_WIDTH/TILE_HEIGHT) / 2f,
            (position.y - position.x*TILE_HEIGHT/TILE_WIDTH) / 2f
        );
    }
}
