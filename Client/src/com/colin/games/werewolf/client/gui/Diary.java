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

import javax.swing.*;
import java.awt.*;

/**
 * The diary panel.
 */
public class Diary extends JPanel {
    /**
     * Constructs a new Diary panel for the game.
     */
    public Diary(){
        JTextArea textArea = new JTextArea();
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textScroll = new JScrollPane(textArea);
        setLayout(new BorderLayout());
        pane.add(textScroll);
        add(pane,BorderLayout.CENTER);
    }
}
