package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.utils.Constants;

public class DesktopLauncher {

	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		config.setResizable(   Constants.WINDOW_RESIZABLE);
		config.setTitle(       Constants.WINDOW_TITLE);
		config.useVsync(       Constants.ENABLE_VSYNC);
		new Lwjgl3Application(new MyGdxGame(), config);
	}

}
