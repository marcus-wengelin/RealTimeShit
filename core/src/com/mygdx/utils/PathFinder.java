package com.mygdx.utils;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Hashtable;

public class PathFinder {
    public static class Node {
        public int x;
        public int y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Node) {
                Node n = (Node)o;
                return (x == n.x && y == n.y);
            } else return false;
        }
    }

    private static TiledMap map = null;

    private PathFinder() {}

    public static void setMap(TiledMap tiledMap) {
        map = tiledMap; 
    }

    private static TiledMapTileLayer getCollisionLayer() {
        return (TiledMapTileLayer)map.getLayers().get("Collision0");
    }

    private static double heuristicCost(Node s, Node g) {
        return Math.sqrt((s.x - g.x)*(s.x - g.x) + (s.y - g.y)*(s.y - g.y));
    }

    private static ArrayList<Node> reconstructPath(Hashtable<Node, Node> cameFrom, Node current) {
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        return path;
    }

    private static boolean validNeighbor(Node n, TiledMapTileLayer collisionLayer, int mapWidth, int mapHeight) {
        return (MathUtils.clamp(n.x, 0, mapWidth-1)  == n.x &&
                MathUtils.clamp(n.y, 0, mapHeight-1) == n.y &&
                collisionLayer.getCell(n.x, n.y)   == null);
    }

    private static ArrayList<Node> getNeighbors(Node n) {
        TiledMapTileLayer collisionLayer = getCollisionLayer();

        MapProperties prop = map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        ArrayList<Node> neighbors = new ArrayList<Node>(); 
        ArrayList<Node> unchecked = new ArrayList<Node>();

        unchecked.add(new Node(n.x-1, n.y-1));
        unchecked.add(new Node(n.x, n.y-1));
        unchecked.add(new Node(n.x+1, n.y-1));
        unchecked.add(new Node(n.x+1, n.y));
        unchecked.add(new Node(n.x+1, n.y+1));
        unchecked.add(new Node(n.x, n.y+1));
        unchecked.add(new Node(n.x-1, n.y+1));
        unchecked.add(new Node(n.x-1, n.y));
        for (Node u : unchecked) {
            if (validNeighbor(u, collisionLayer, mapWidth, mapHeight))
                neighbors.add(u);
        }
        return neighbors;
    }

    public static ArrayList<Node> aStarSearch(int startX, int startY, int goalX, int goalY) {
        TiledMapTileLayer collisionLayer = getCollisionLayer();

        Node start = new Node(startX, startY);
        Node goal  = new Node(goalX, goalY);

        ArrayList<Node> closedSet = new ArrayList<Node>();
        ArrayList<Node> openSet   = new ArrayList<Node>();
        openSet.add(start);

        Hashtable<Node, Node> cameFrom = new Hashtable<Node, Node>();
        Hashtable<Node, Double> gScore = new Hashtable<Node, Double>() {
            @Override public Double get(Object o) {
                if (containsKey(o))
                    return super.get(o);
                return Double.POSITIVE_INFINITY;
            }
        };
        Hashtable<Node, Double> fScore = new Hashtable<Node, Double>() {
            @Override public Double get(Object o) {
                if (containsKey(o))
                    return super.get(o);
                return Double.POSITIVE_INFINITY;
            }
        };
        
        gScore.put(start, 0D);
        fScore.put(start, heuristicCost(start, goal));

        while (!openSet.isEmpty()) {
            Node current  = null;
            double cScore = Double.POSITIVE_INFINITY;
            /* Get the node with the lowest fscore value */
            for (Node node : openSet) {
                if (fScore.get(node) < cScore) {
                    current = node;
                    cScore = fScore.get(node);
                }
            }

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (Node neighbor : getNeighbors(current)) {
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
        return new ArrayList<Node>();
    }
}
