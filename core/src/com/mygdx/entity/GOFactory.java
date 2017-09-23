package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class GOFactory {

    private GOFactory() {}

    public static AnimatedObject makePlayer(int col, int row) {
        Texture ss = new Texture("sprites/skeleton_0.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int width  = 64;
        int height = 64;
        int startX = 256;
        int startY = 384;
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(ss, startX+i*width, startY, width, height));
        Animation<TextureRegion> anim = new Animation<TextureRegion>(0.1f, frames, PlayMode.LOOP);
        
        return new AnimatedObject(anim, new GridPoint2(col, row));
    }

}
