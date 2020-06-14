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

/**
 * A utility class for knowing about the running environment.
 */
public class Environment {
    private static boolean isModded = false;
    private static Side side;
    private Environment(){
        throw new AssertionError();
    }
    public static void setSide(Side given){
        side = given;
    }
    public static boolean isServer(){
        return side.equals(Side.SERVER);
    }
    public static boolean isClient(){
        return side.equals(Side.CLIENT);
    }
    public static Path workingDir(){
        return Paths.get(System.getProperty("user.dir"));
    }
    public static boolean isModded(){
        return isModded;
    }
    public static void setModded(boolean toSet){
        isModded = toSet;
    }
    public static Side getSide(){
        return side;
    }
    public enum Side{
        SERVER,CLIENT
    }
}
