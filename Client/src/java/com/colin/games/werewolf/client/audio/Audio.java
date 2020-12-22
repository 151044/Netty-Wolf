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

package com.colin.games.werewolf.client.audio;

import com.colin.games.werewolf.common.Environment;
import com.sun.jna.Native;

/**
 * Wrapper around the native C audio library, with a SDL backend.
 */
public class Audio {
    private static boolean isLoaded = false;
    static{
        if(!Environment.getOperatingSystem().equals(Environment.OperatingSystem.MAC)){
            Native.register("audio");
            isLoaded = true;
        }
    }

    /**
     * Initializes SDL. Needs to be called first.
     * @return True if initialization succeeds, false otherwise
     */
    public static native boolean initSDL();

    /**
     * Gets the path of the playing music.
     * @return The path of the music which is being played
     */
    public static native String getPlaying();

    /**
     * Plays a music file.
     * The file can be in .mp3,.wav,.ogg, and others. Consult the SDL documentation for details.
     * @param path The path of the file to play
     * @return True if the music is played successfully, false otherwise
     * @see <a href="https://wiki.libsdl.org/"></a>
     */
    public static native boolean playMusic(String path);

    /**
     * Plays a sound file.
     * The file can be in .mp3,.wav,.ogg, and others. Consult the SDL documentation for details.
     * @param path The path of the file to play.
     * @param suspendMusic True if the music should be suspended when playing the sound
     * @return True if the sound is played successfully, false otherwise
     * @see <a href="https://wiki.libsdl.org/"></a>
     */
    public static native boolean playSound(String path, boolean suspendMusic);

    /**
     * Sets the volume of both the music and the sounds.
     * Identical to calling {@link #setMusicVolume(int)} and {@link #setSoundVolume(int)} sequentially.
     * @param vol The volume to set; The number should be from 0 to 128
     */
    public static native void setVolume(int vol);

    /**
     * Cleans up the SDL backend.
     * Highly recommended to be called.
     */
    public static native void quitSDL();

    /**
     * Sets the volume of the music.
     * @param i The volume to set; The number should be from 0 to 128
     */
    public static native void setMusicVolume(int i);

    /**
     * Sets the volume of the sounds.
     * @param i The volume to set; The number should be from 0 to 128
     */
    public static native void setSoundVolume(int i);

    /**
     * Tests if the sound is being played.
     * Might not work, from my own testing.
     * @return True if sound is being played; false otherwise
     */
    public static native boolean isPlayingMusic();

    /**
     * Tests if the sound system is loaded.
     * @return True if the sound system is loaded, false otherwise
     */
    public static boolean isSoundLoaded(){
        return isLoaded;
    }

    /**
     * Sets the availability of the sound system.
     * @param b The availability to set
     */
    public static void setSoundAvailable(boolean b){
        isLoaded = b;
    }

    /**
     * Gets the volume of the music.
     * @return The volume of the music, in the range of 0 to 128
     */
    public static native int getMusicVolume();
}
