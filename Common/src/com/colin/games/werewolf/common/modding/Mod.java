/*
 * Netty-Wolf
 * Copyright (C) 2020  Colin Chow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.colin.games.werewolf.common.modding;

import com.colin.games.werewolf.common.modding.gui.DefaultModScreen;

import java.util.List;

/**
 * The mod interface. All modifications must implement this interface to be recognized as a mod and loaded.
 */
public interface Mod {
    /**
     * Gets if the mod is server-side capable.
     * Mods cannot be loaded if they are of a different side as they are written for.
     * @return True if the mod is server-side capable, false otherwise
     */
    boolean isServer();
    /**
     * Gets if the mod is client-side capable.
     * Mods cannot be loaded if they are of a different side as they are written for.
     * @return True if the mod is client-side capable, false otherwise
     */
    boolean isClient();

    /**
     * Returns a list of modified parts of the game.
     * @return A list, holding the modules modified by this mod
     */
    List<ModType> modified();

    /**
     * Initializes the mod.
     * Ran at the start of each starting run, just after the mods are discovered and run.
     */
    void init();

    /**
     * Late-initializes the mod.
     * Ran after all base game behaviour has been registered.
     */
    void lateInit();

    /**
     * Cleans up any leftover resources that the mod may be holding.
     */
    void cleanup();

    /**
     * Gets the name of the mod.
     * @return The name of this mod
     */
    String name();

    /**
     * Gets the author of this mod.
     * @return The author of this modification
     */
    String author();

    /**
     * Gets the version of this mod.
     * @return A string describing the version
     */
    String version();

    /**
     * A description about your mod.
     * @return A short description, or the default value if none is specified
     */
    default String desc(){
        return "The default description. Shouldn't you change it?";
    }

    /**
     * Shows information about your mod.
     */
    default void infoScreen(){
        new DefaultModScreen(this);
    }
}
