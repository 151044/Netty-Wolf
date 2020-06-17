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

/**
 * Shows the About GUI.
 */
public class AboutFrame extends JFrame {
    /**
     * Constructs a new AboutFrame.
     */
    public AboutFrame() {
        super("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        jta.setText("About:\n" +
                "This is a game.");
        add(jta);
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
        JButton thanks = new JButton("Acknowledgements");
        buttons.add(thanks);
        thanks.addActionListener(ignored -> new ThanksFrame());
        JButton gpl = new JButton("License");
        buttons.add(gpl);
        gpl.addActionListener(ignored -> new FreeFrame());
        JButton back = new JButton("Back");
        buttons.add(back);
        back.addActionListener(ignored -> dispose());
        add(buttons);
        pack();
        setVisible(true);
    }
}
