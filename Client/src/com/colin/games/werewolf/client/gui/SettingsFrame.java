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
 * The GUI for selecting which settings to tweak.
 */
public class SettingsFrame extends JFrame {
    /**
     * Creates a new SettingFrame.
     */
    public SettingsFrame(){
        super("Settings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
        JPanel defaults = new JPanel();
        defaults.setLayout(new BoxLayout(defaults,BoxLayout.Y_AXIS));
        JButton theme = new JButton("Theme");
        theme.addActionListener((ae) -> {
           dispose();
           new ThemeFrame();
        });
        defaults.add(theme);
        add(defaults);
        JPanel exit = new JPanel();
        exit.setLayout(new BoxLayout(exit,BoxLayout.Y_AXIS));
        JButton back = new JButton("Back");
        back.addActionListener(ae -> {
            dispose();
            new StartMenu();
        });
        exit.add(back);
        add(exit);
        pack();
        setVisible(true);
    }
}
