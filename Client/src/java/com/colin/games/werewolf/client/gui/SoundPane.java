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

package com.colin.games.werewolf.client.gui;

import com.colin.games.werewolf.client.audio.Audio;

import javax.swing.*;

public class SoundPane extends JPanel {
    public SoundPane(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JSlider volume = new JSlider(JSlider.HORIZONTAL,0,100,30);
        volume.addChangeListener((ce) -> {
            if(volume.getValueIsAdjusting()){
                return;
            }
            Audio.setVolume((int) (volume.getValue() / 100.0f * 128));
        });
        //Turn on labels at major tick marks.
        volume.setMajorTickSpacing(20);
        volume.setMinorTickSpacing(5);
        volume.setPaintTicks(true);
        volume.setPaintLabels(true);
        JPanel layout = new JPanel();
        layout.setLayout(new BoxLayout(layout,BoxLayout.X_AXIS));
        layout.add(new JLabel("Volume:"));
        layout.add(volume);
        add(layout);
    }

}
