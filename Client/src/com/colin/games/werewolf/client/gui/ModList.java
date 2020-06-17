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

import com.colin.games.werewolf.common.modding.Mod;
import com.colin.games.werewolf.common.modding.ModLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Shows the list of mods.
 */
public class ModList extends JFrame {
    /**
     * Constructs a new ModList.
     */
    public ModList(){
        super("Mods Installed");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        DefaultListModel<Mod> lm = new DefaultListModel<>();
        List<Mod> list;
        try {
            list = ModLoader.loadMods(null,false);
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Mod list is not initialized.");
        }
        list.forEach(lm::addElement);
        JList<Mod> show = new JList<>(lm);
        show.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        show.setLayoutOrientation(JList.VERTICAL);
        show.setModel(lm);
        JButton info = new JButton("Show info");
        info.setMaximumSize(info.getPreferredSize());
        info.addActionListener((ignored) ->{
            Mod am = show.getSelectedValue();
            am.infoScreen();
        });
        show.addListSelectionListener((lse) ->{
            if(!lse.getValueIsAdjusting()){
                info.setEnabled(show.getSelectedIndex() != -1);
            }
        });
        JScrollPane jsp = new JScrollPane(show);
        jsp.setPreferredSize(jsp.getPreferredSize());
        JButton back = new JButton("Back to Menu");
        back.addActionListener((ignored) -> dispose());
        info.setEnabled(false);
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(info);
        buttons.add(back);
        add(jsp);
        add(buttons);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
