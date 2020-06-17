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

package com.colin.games.werewolf.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A utility class for learning about the running environment.
 */
public class Environment {
    private static boolean isModded = false;
    private static Side side;
    private Environment(){
        throw new AssertionError();
    }

    /**
     * Sets the {@link com.colin.games.werewolf.common.Environment.Side Side} of this running game.
     * Should not be called from application code.
     * @param given The side to set. If null is passed, throws a {@link java.lang.NullPointerException NullPointerException}.
     */
    public static void setSide(Side given){
        Objects.requireNonNull(given);
        side = given;
    }

    /**
     * Checks if the game instance is a server.
     * @return True if this is a server, false otherwise
     */
    public static boolean isServer(){
        return side.equals(Side.SERVER);
    }

    /**
     * Checks if the game instance is a client.
     * @return True if this is a client, false otherwise
     */
    public static boolean isClient(){
        return side.equals(Side.CLIENT);
    }

    /**
     * Gets the working directory of this game.
     * Note that this is a wrapper around {@link System#getProperty(String) System.getProperty()}.
     * @return The path of the current directory
     */
    public static Path workingDir(){
        return Paths.get(System.getProperty("user.dir"));
    }

    /**
     * Tests if the game instance is modified by mods.
     * @return True if this game is modded, false otherwise
     */
    public static boolean isModded(){
        return isModded;
    }

    /**
     * Sets if this game is modded.
     * Should not be called from application code.
     * @param toSet Whether the game is modded
     */
    public static void setModded(boolean toSet){
        isModded = toSet;
    }

    /**
     * Gets the side of the currently running game.
     * @return The side of the game
     */
    public static Side getSide(){
        return side;
    }

    /**
     * This enum shows which side this instance is: Client or Server.
     * It is useful for differentiating which side your code is running on.
     */
    public enum Side{
        /**
         * The Server side.
         */
        SERVER,
        /**
         * The Client side.
         */
        CLIENT
    }
}
