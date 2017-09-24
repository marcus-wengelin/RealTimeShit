package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import java.util.Hashtable;
import java.util.ArrayList;

public class GoFactory {

    private GoFactory() {}

    private static Animation<TextureRegion> loadAnim(Texture ss, int sx, int sy, int w, int h, int n) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < n; i++) frames.add(new TextureRegion(ss, sx+i*w, sy, w, h));
        return new Animation<TextureRegion>(0.1f, frames, PlayMode.LOOP);
    }

    public static MovableGo makePlayer(int col, int row) {
        Texture ss = new Texture("sprites/skeleton_0.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int width  = 64;
        int height = 64;
        int startX = 256;
        int size   = 8;

        Hashtable<GoState, Animation<TextureRegion>> animTable =
            new Hashtable<GoState, Animation<TextureRegion>>();

        animTable.put(GoState.MOVING_W, loadAnim(ss, startX, 0,   width, height, size));
        animTable.put(GoState.MOVING_N, loadAnim(ss, startX, 128, width, height, size));
        animTable.put(GoState.MOVING_E, loadAnim(ss, startX, 256, width, height, size));
        animTable.put(GoState.MOVING_S, loadAnim(ss, startX, 384, width, height, size));
        ArrayList<GoState> allowedStates = new ArrayList<GoState>();
        allowedStates.add(GoState.MOVING_E);
        allowedStates.add(GoState.MOVING_W);
        allowedStates.add(GoState.MOVING_N);
        allowedStates.add(GoState.MOVING_S);

        return new MovableGo(new GridPoint2(col, row), animTable, allowedStates, GoState.MOVING_E, 1f);
    }

    public static GameObject makeMarker(int col, int row) {
        Texture tx = new Texture("sprites/marker.png");
        ArrayList<GoState> allowedStates = new ArrayList<GoState>();
        allowedStates.add(GoState.IDLE);
        return new GameObject(tx, new GridPoint2(col, row), allowedStates, GoState.IDLE);
    }

}
