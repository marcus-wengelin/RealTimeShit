package com.mygdx.input;

import com.mygdx.game.WorldApi;

/**
 * Interface for command objects
 */
public interface Command {
    public boolean execute(WorldApi api);
}
