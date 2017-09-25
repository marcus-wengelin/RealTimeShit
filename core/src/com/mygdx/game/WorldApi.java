package com.mygdx.game;

import com.mygdx.entity.GameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Provides limited access to game state
 */
public interface WorldApi {

    public GameObject getSelectedUnit();

    public GridPoint2 mouseToGrid(int x, int y);

    public OrthographicCamera getOrthoCamera();
}
