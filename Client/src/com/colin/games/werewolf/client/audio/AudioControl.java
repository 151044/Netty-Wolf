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

/**
 * Main audio control class.
 */
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

    /**
     * Sets the background music.
     * @param soundPath The path of the music file to play
     * @throws IOException When the file cannot be read
     * @throws UnsupportedAudioFileException When this audio format is not supported
     * @throws LineUnavailableException When a line is not available
     */
    public static void setBackground(URL soundPath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(background != null){
            background.close();
            background = AudioSystem.getClip();
        }
        background.open(AudioSystem.getAudioInputStream(soundPath));
        background.setFramePosition(0);
        background.loop(Clip.LOOP_CONTINUOUSLY);
        background.start();
    }

    /**
     * Stops the background music.
     */
    public static void stopBackground(){
        background.stop();
    }

    /**
     * Restarts the background music.
     */
    public static void restartBackground() {
        background.start();
    }

    /**
     * Sets the volume of the background music.
     * @param vol The volume to set, from 0.0 to 1.0
     */
    public static void setVolume(float vol){
        if(vol < 0.0f || vol > 1.0f){
            throw new IllegalArgumentException("Volume is not between 0.0 to 1.0!");
        }
        FloatControl fCon = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
        fCon.setValue((float) Math.log10(vol) * 20.0f);
    }

    /**
     * Plays an arbitrary sound clip.
     * @param clip The clip to play
     * @param pauseBackground Whether to pause the background music
     * @throws IOException When the file cannot be read
     * @throws UnsupportedAudioFileException When this audio format is not supported
     * @throws LineUnavailableException When a line is not available
 */
    public static void playClip(URL clip, boolean pauseBackground) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip load = AudioSystem.getClip();
        load.open(AudioSystem.getAudioInputStream(clip));
        if(pauseBackground){
            stopBackground();
            load.addLineListener(lineEvent -> {
                if(lineEvent.getType().equals(LineEvent.Type.STOP)){
                    restartBackground();
                }
            });
        }
        load.start();
    }
}
