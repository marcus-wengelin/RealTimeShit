package com.mygdx.utils;

import com.badlogic.gdx.utils.Logger;

public class Constants {

	private Constants() {}

	public static final String  WINDOW_TITLE     = "Placeholder";
	public static final int     WINDOW_WIDTH     = 640;
	public static final int     WINDOW_HEIGHT    = 480;
	public static final boolean WINDOW_RESIZABLE = true;
	public static final boolean ENABLE_VSYNC     = false;
	public static final int     LOG_LEVEL        = Logger.DEBUG; // DEBUG, ERROR, INFO, NONE

	public static final int   UPDATES_PER_SECOND       = 60;
	public static final int   MAX_FRAMES_PER_SECOND    = 300;
	public static final float MAX_TIME_BETWEEN_UPDATES = 0.25f; // in seconds

	public static final int TILE_WIDTH  = 64;
	public static final int TILE_HEIGHT = 32;

}
