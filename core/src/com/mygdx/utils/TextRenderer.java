package com.mygdx.utils;

import java.util.Hashtable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

public class TextRenderer {

    public enum Alignment {
        CENTER, LEFT,        RIGHT,
        TOP,    TOP_LEFT,    TOP_RIGHT,
        BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    private static boolean                       initted;
    private static Hashtable<String, BitmapFont> fonts;
    private static GlyphLayout                   layout;

    private TextRenderer() {}

    public static void init() {
        assert !initted;
        initted = true;
        fonts   = new Hashtable<String, BitmapFont>();
        layout  = new GlyphLayout();
    }

    public static void loadFonts(String path) {
        assert initted;
        for (FileHandle f : Gdx.files.internal(path).list()) {
            if (!f.extension().toLowerCase().equals("fnt")) continue;
            fonts.put(f.nameWithoutExtension().toLowerCase(), new BitmapFont(f));
        }
        Gdx.app.log("TextRenderer", "loaded "+fonts.size()+" font(s)");
    }

    public static BitmapFont getFont(String fontName) {
        assert initted;
        return fonts.get(fontName);
    }

    public static boolean draw(SpriteBatch batch, String fontName, String text, float x, float y, Alignment alignment) {
        return drawOnWorld(batch, fontName, text, x, y, alignment);
    }

    // @TODO: parameter names x and y are bad
    public static boolean drawOnScreen(SpriteBatch batch, String fontName, String text, float x, float y, Alignment alignment) {
        assert initted;
        if (x < 0 || x > 1 || y < 0 || y > 1) return false;
        float newX = x     * Gdx.graphics.getWidth();
        float newY = (1-y) * Gdx.graphics.getHeight(); // flip Y axis
        return drawOnWorld(batch, fontName, text, newX, newY, alignment);
    }

    public static boolean drawOnWorld(SpriteBatch batch, String fontName, String text, float x, float y, Alignment alignment) {
        assert initted;
        BitmapFont font = fonts.get(fontName);
        if (font == null) return false;

        layout.setText(font, text);
        switch (alignment) {
            case CENTER:
                x -= layout.width/2f;
                y += layout.height/2f;
                break;
            case LEFT:
                x -= layout.width;
                y += layout.height/2f;
                break;
            case RIGHT:
                y += layout.height/2f;
                break;
            case TOP:
                x -= layout.width/2f;
                y += layout.height;
                break;
            case TOP_LEFT:
                x -= layout.width;
                y += layout.height;
                break;
            case TOP_RIGHT:
                y += layout.height;
                break;
            case BOTTOM:
                x -= layout.width/2f;
                break;
            case BOTTOM_LEFT:
                x -= layout.width;
                break;
            case BOTTOM_RIGHT:
                break;
            default:
                // there is an alignment we don't account for
                assert false;
        }
        font.draw(batch, layout, x, y);
        return true;
    }

    public static void dispose() {
        assert initted;
        for (BitmapFont f : fonts.values()) f.dispose();
        fonts.clear();
        initted = false;
        fonts   = null;
        layout  = null;
    }

}
