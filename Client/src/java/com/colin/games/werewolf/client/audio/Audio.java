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

public class Audio {
    private static boolean isLoaded = false;
    static{
        if(!Environment.getOperatingSystem().equals(Environment.OperatingSystem.MAC)){
            Native.register("audio");
            isLoaded = true;
        }
    }
    public static native boolean initSDL();
    public static native String getPlaying();
    public static native boolean playMusic(String path);
    public static native boolean playSound(String path, boolean suspendMusic);
    public static native void setVolume(int vol);
    public static native void quitSDL();
    public static native int getVolume();
    public static native void setMusicVolume();
    public static native void setSoundVolume();
    public static boolean isSoundLoaded(){
        return isLoaded;
    }
    public static void setSoundAvailable(boolean b){
        isLoaded = b;
    }
}
