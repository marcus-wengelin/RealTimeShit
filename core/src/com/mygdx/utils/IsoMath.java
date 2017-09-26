package com.mygdx.utils;

import static com.mygdx.utils.Constants.TILE_WIDTH;
import static com.mygdx.utils.Constants.TILE_HEIGHT;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class IsoMath {
    private IsoMath() {}

    /**
     * Returns true if goal is north of origin
     */
    public static boolean isNorthOf(GridPoint2 origin, GridPoint2 goal) {
        return goal.y > origin.y;
    }
    public static boolean isNorthOf(Vector2 origin, Vector2 goal) {
        return goal.y > origin.y;
    }
    public static boolean isNorthOf(GridPoint2 origin, Vector2 goal) {
        return goal.y > gridToWorld(origin).y;
    }
    public static boolean isNorthOf(Vector2 origin, GridPoint2 goal) {
        return gridToWorld(goal).y > origin.y;
    }

    /**
     * Returns true if goal is south of origin
     */
    public static boolean isSouthOf(GridPoint2 origin, GridPoint2 goal) {
        return goal.y < origin.y;
    }
    public static boolean isSouthOf(Vector2 origin, Vector2 goal) {
        return goal.y < origin.y;
    }
    public static boolean isSouthOf(GridPoint2 origin, Vector2 goal) {
        return goal.y < gridToWorld(origin).y;
    }
    public static boolean isSouthOf(Vector2 origin, GridPoint2 goal) {
        return gridToWorld(goal).y < origin.y;
    }

    /**
     * Returns true if goal is east of origin
     */
    public static boolean isEastOf(GridPoint2 origin, GridPoint2 goal) {
        return goal.x > origin.x;
    }
    public static boolean isEastOf(Vector2 origin, Vector2 goal) {
        return goal.x > origin.x;
    }
    public static boolean isEastOf(GridPoint2 origin, Vector2 goal) {
        return goal.x > gridToWorld(origin).x;
    }
    public static boolean isEastOf(Vector2 origin, GridPoint2 goal) {
        return gridToWorld(goal).x > origin.x;
    }

    /**
     * Returns true if goal is west of origin
     */
    public static boolean isWestOf(GridPoint2 origin, GridPoint2 goal) {
        return goal.x < origin.x;
    }
    public static boolean isWestOf(Vector2 origin, Vector2 goal) {
        return goal.x < origin.x;
    }
    public static boolean isWestOf(GridPoint2 origin, Vector2 goal) {
        return goal.x < gridToWorld(origin).x;
    }
    public static boolean isWestOf(Vector2 origin, GridPoint2 goal) {
        return gridToWorld(goal).x < origin.x;
    }

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
    //@TODO: this method is not used anywhere, remove it?
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
    //@TODO: this method is not used anywhere, remove it?
    public static Vector2 worldToScreen(Vector2 position) {
        return new Vector2(
            (position.x + position.y * TILE_WIDTH/TILE_HEIGHT),
            (position.y - position.x * TILE_HEIGHT/TILE_WIDTH) / 2f
        );
    }

    //@TODO: When selecting a tile outside of the map this function
    // returns invalid coordinates.
    public static GridPoint2 orthoWorldToIso(Vector2 position) {
        return new GridPoint2(
            (int) ((position.x/TILE_WIDTH - position.y/(TILE_HEIGHT/2f)) / 2f + 0.5f),
            (int) ((position.x/TILE_WIDTH + position.y/(TILE_HEIGHT/2f)) / 2f - 0.5f)
        );
    }
}
