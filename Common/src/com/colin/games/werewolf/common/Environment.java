package com.colin.games.werewolf.common;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for knowing about the running environment.
 */
public class Environment {
    private Environment(){
        throw new AssertionError();
    }
    private static Side side;
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
    public enum Side{
        SERVER,CLIENT
    }
}
