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

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioControl {
    private AudioControl(){
        throw new AssertionError();
    }
    private static Clip background;

    static {
        try {
            background = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void setBackground(URL soundPath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(background != null){
            background.close();
        }
        background.open(AudioSystem.getAudioInputStream(soundPath));
        background.setFramePosition(0);
        background.start();
        background.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void stopBackground(){
        background.stop();
    }
    public static void restartBackground() {
        background.start();
    }
    public static void setVolume(float vol){
        FloatControl fCon = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
        fCon.setValue((float) Math.log10(vol) * 20.0f);
    }
}
