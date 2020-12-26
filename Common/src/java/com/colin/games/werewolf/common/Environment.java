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

import com.colin.games.werewolf.common.modding.ModType;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A utility class for learning about the running environment.
 */
public class Environment {
    private static boolean isModded = false;
    private static Side side;
    private static final Set<ModType> modified = new HashSet<>();
    private static final OperatingSystem os;
    static{
        String osStr = System.getProperty("os.name");
        if(osStr == null){
            os = OperatingSystem.UNKNOWN;
        }else if(osStr.contains("Linux")){
            os = OperatingSystem.LINUX;
        }else if(osStr.toLowerCase().contains("windows")){
            os = OperatingSystem.WINDOWS;
        }else if(osStr.toLowerCase().contains("mac")){
            os = OperatingSystem.MAC;
        }else{
            os = OperatingSystem.UNKNOWN;
        }
    }
    private Environment(){
        throw new AssertionError();
    }

    /**
     * Sets the {@link Environment.Side Side} of this running game.
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
     * @return The path of the directory to put configs in
     */
    public static Path homeDir(){
        return Path.of(System.getProperty("user.home"));
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

    /**
     * Adds these modules to the modified list.
     * @param modifications The list of modified modules
     */
    public static void addModified(List<ModType> modifications){
        modified.addAll(modifications);
    }

    /**
     * Sets the current look and feel.
     * @param feel The string describing the look and feel, as shown above
     */
    public static void setLookAndFeel(String feel){
        LookAndFeel toSet;
        switch (feel) {
            case "light":
                toSet = new FlatLightLaf();
                break;
            case "dark":
                toSet = new FlatDarkLaf();
                break;
            case "intellij":
                toSet = new FlatIntelliJLaf();
                break;
            case "darcula":
                toSet = new FlatDarculaLaf();
                break;
            default:
                toSet = new NimbusLookAndFeel();
                break;
        }
        try{
            UIManager.setLookAndFeel(toSet);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        for (Window w : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(w);
        }
    }
    public static OperatingSystem getOperatingSystem(){
        return os;
    }
    public enum OperatingSystem{
        WINDOWS,MAC,LINUX,UNKNOWN
    }
    public static Path getStorePath(){
        if(os.equals(OperatingSystem.MAC)){
            return homeDir().resolve(Path.of("~/Library/Application Support/Netty-Wolf/"));
        }else if(os.equals(OperatingSystem.LINUX)){
            return homeDir().resolve(Path.of(".local/share/Netty-Wolf/"));
        }else{
            return homeDir().resolve("\\AppData\\Roaming\\Netty-Wolf");
        }
    }
}
