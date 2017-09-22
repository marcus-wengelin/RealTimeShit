package com.mygdx.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PathFinder {
    private static TiledMap map = null;

    private PathFinder() {}

    public static void setMap(TiledMap tiledMap) {
        map = tiledMap; 
    }

    public static boolean aStarSearch(int startX, int startY, int goalX, int goalY) {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get("Collision0");
        
    }

}
