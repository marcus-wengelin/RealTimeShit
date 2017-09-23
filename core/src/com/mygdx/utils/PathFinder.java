package com.mygdx.utils;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Hashtable;

public class PathFinder {

    private static TiledMap map = null;

    private PathFinder() {}

    public static void setMap(TiledMap tiledMap) {
        map = tiledMap;
    }

    private static TiledMapTileLayer getCollisionLayer() {
        return (TiledMapTileLayer)map.getLayers().get("Collision0");
    }

    private static double heuristicCost(GridPoint2 s, GridPoint2 g) {
        return Math.sqrt((s.x - g.x)*(s.x - g.x) + (s.y - g.y)*(s.y - g.y));
    }

    private static ArrayList<GridPoint2> reconstructPath(Hashtable<GridPoint2, GridPoint2> cameFrom, GridPoint2 current) {
        ArrayList<GridPoint2> path = new ArrayList<GridPoint2>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        return path;
    }

    private static boolean validNeighbor(GridPoint2 n, TiledMapTileLayer collisionLayer, int mapWidth, int mapHeight) {
        return (MathUtils.clamp(n.x, 0, mapWidth-1)  == n.x &&
                MathUtils.clamp(n.y, 0, mapHeight-1) == n.y &&
                collisionLayer.getCell(n.x, n.y)   == null);
    }

    private static ArrayList<GridPoint2> getNeighbors(GridPoint2 n) {
        TiledMapTileLayer collisionLayer = getCollisionLayer();

        MapProperties prop = map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        ArrayList<GridPoint2> neighbors = new ArrayList<GridPoint2>();
        ArrayList<GridPoint2> unchecked = new ArrayList<GridPoint2>();

        unchecked.add(new GridPoint2(n.x-1, n.y-1));
        unchecked.add(new GridPoint2(n.x, n.y-1));
        unchecked.add(new GridPoint2(n.x+1, n.y-1));
        unchecked.add(new GridPoint2(n.x+1, n.y));
        unchecked.add(new GridPoint2(n.x+1, n.y+1));
        unchecked.add(new GridPoint2(n.x, n.y+1));
        unchecked.add(new GridPoint2(n.x-1, n.y+1));
        unchecked.add(new GridPoint2(n.x-1, n.y));
        for (GridPoint2 u : unchecked) {
            if (validNeighbor(u, collisionLayer, mapWidth, mapHeight))
                neighbors.add(u);
        }
        return neighbors;
    }

    public static ArrayList<GridPoint2> aStarSearch(int startX, int startY, int goalX, int goalY) {
        TiledMapTileLayer collisionLayer = getCollisionLayer();

        GridPoint2 start = new GridPoint2(startX, startY);
        GridPoint2 goal  = new GridPoint2(goalX, goalY);

        ArrayList<GridPoint2> closedSet = new ArrayList<GridPoint2>();
        ArrayList<GridPoint2> openSet   = new ArrayList<GridPoint2>();
        openSet.add(start);

        Hashtable<GridPoint2, GridPoint2> cameFrom = new Hashtable<GridPoint2, GridPoint2>();
        Hashtable<GridPoint2, Double> gScore = new Hashtable<GridPoint2, Double>() {
            @Override public Double get(Object o) {
                if (containsKey(o))
                    return super.get(o);
                return Double.POSITIVE_INFINITY;
            }
        };
        Hashtable<GridPoint2, Double> fScore = new Hashtable<GridPoint2, Double>() {
            @Override public Double get(Object o) {
                if (containsKey(o))
                    return super.get(o);
                return Double.POSITIVE_INFINITY;
            }
        };

        gScore.put(start, 0D);
        fScore.put(start, heuristicCost(start, goal));

        while (!openSet.isEmpty()) {
            GridPoint2 current  = null;
            double cScore = Double.POSITIVE_INFINITY;
            /* Get the GridPoint2 with the lowest fscore value */
            for (GridPoint2 GridPoint2 : openSet) {
                if (fScore.get(GridPoint2) < cScore) {
                    current = GridPoint2;
                    cScore = fScore.get(GridPoint2);
                }
            }

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (GridPoint2 neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor))
                    continue;
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);
                /* Obs, should be distBetween(current, neighbor) */
                double tentativeScore = gScore.get(current) + heuristicCost(current, neighbor);
                if (tentativeScore >= gScore.get(neighbor))
                    continue;

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeScore);
                fScore.put(neighbor, gScore.get(neighbor) + heuristicCost(neighbor, goal));
            }
        }
        return new ArrayList<GridPoint2>();
    }
}
