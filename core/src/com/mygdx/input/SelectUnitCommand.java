package com.mygdx.input;

import com.mygdx.entity.GameObject;
import com.mygdx.game.WorldApi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

public class SelectUnitCommand implements Command {

    private int startX, startY, endX, endY;
    public SelectUnitCommand(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX   = endX;
        this.endY   = endY;
    }

    @Override public boolean execute(WorldApi api) {
        ArrayList<GameObject> selectedUnitList = api.getSelectedUnits();
        /*@TODO: Optimize to only remove entities not in our selected area */
        if (!selectedUnitList.isEmpty()) selectedUnitList.clear();

        GridPoint2 startCell = api.mouseToGrid(this.startX, this.startY);
        GridPoint2 endCell   = api.mouseToGrid(this.endX, this.endY);

        Gdx.app.debug("SelectUnitCommand", ""+startCell);
        Gdx.app.debug("SelectUnitCommand", ""+endCell);

        ArrayList<GameObject> selectedUnits = new ArrayList<GameObject>();
        if (startCell.equals(endCell)) {
            selectedUnits.addAll(api.getUnitsInCell(startCell));
        } else {
            /*@TODO: This logic is flawed */
            int deltaX = startCell.x < endCell.x ? 1 : -1;
            int deltaY = startCell.y < endCell.y ? 1 : -1;
            for (int i = startCell.x; i != endCell.x; i+=deltaX) {
                for (int j = startCell.y; j != endCell.y; j+=deltaY) {
                    GridPoint2 cell = new GridPoint2(i,j);
                    Gdx.app.debug("SelectUnitCommand", "Checking cell:"+cell);
                    selectedUnits.addAll(api.getUnitsInCell(cell));
                }
            }
        }
        api.setSelectedUnits(selectedUnits);
        return true;
    }

}
