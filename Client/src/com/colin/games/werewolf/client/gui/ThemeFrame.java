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

import com.colin.games.werewolf.common.Environment;

import javax.swing.*;

public class ThemeFrame extends JFrame {
    public ThemeFrame(){
        super("Choose a theme!");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        JRadioButton nimbus = new JRadioButton("Nimbus");
        JRadioButton light = new JRadioButton("Light");
        JRadioButton dark =  new JRadioButton("Dark");
        JRadioButton intellij = new JRadioButton("Inverted Light");
        JRadioButton dracula = new JRadioButton("Dracula");
        group.add(nimbus);
        group.add(light);
        group.add(dark);
        group.add(intellij);
        group.add(dracula);
        JPanel layout = new JPanel();
        layout.setLayout(new BoxLayout(layout,BoxLayout.X_AXIS));
        layout.add(nimbus);
        layout.add(light);
        layout.add(dark);
        layout.add(intellij);
        layout.add(dracula);
        nimbus.addActionListener(ignored -> Environment.setLookAndFeel("nimbus",this));
        light.addActionListener(ignored -> Environment.setLookAndFeel("light",this));
        dark.addActionListener(ignored -> Environment.setLookAndFeel("dark",this));
        intellij.addActionListener(ignored -> Environment.setLookAndFeel("intellij",this));
        dracula.addActionListener(ignored -> Environment.setLookAndFeel("dracula",this));
        add(layout);
        JButton back = new JButton("Back");
        add(back);
        back.addActionListener(ignored-> {
            dispose();
            new StartMenu();
        });
        pack();
        setVisible(true);
    }
}
