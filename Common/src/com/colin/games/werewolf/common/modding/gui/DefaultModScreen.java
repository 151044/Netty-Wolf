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

package com.colin.games.werewolf.common.modding.gui;

import com.colin.games.werewolf.common.modding.Mod;
import com.colin.games.werewolf.common.modding.ModType;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultModScreen extends JFrame {
    public DefaultModScreen(Mod mod){
        super(mod.name());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        add(new JLabel("Name: " + mod.name()));
        add(new JLabel("Version: " + mod.version()));
        add(new JLabel("Author: " + mod.author()));
        add(new JLabel("Description: " + mod.desc()));
        add(new JLabel("Changed Modules: " + String.join(",",mod.modified().isEmpty() ? List.of("None") : mod.modified().stream().map(ModType::getName).collect(Collectors.toList()))));
        pack();
        setVisible(true);
    }
}