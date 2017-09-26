package com.mygdx.game;

import com.mygdx.entity.GameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

/**
 * Provides limited access to game state
 */
public interface WorldApi {

    public ArrayList<GameObject> getSelectedUnits();

    public void setSelectedUnits(ArrayList<GameObject> selectedUnits);

    public ArrayList<GameObject> getUnitsInCell(GridPoint2 cell);

    public GridPoint2 mouseToGrid(int x, int y);

    public OrthographicCamera getOrthoCamera();

}
