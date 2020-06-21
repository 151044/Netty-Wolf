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

public class InGameMenu extends JFrame {
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> players;
    public InGameMenu(){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel playP = new JPanel();
        playP.setLayout(new BoxLayout(playP,BoxLayout.Y_AXIS));
        players = new JList<>(model);
        playP.add(players);
        add(playP);
        JPanel manage = new JPanel();
        manage.setLayout(new BoxLayout(manage,BoxLayout.X_AXIS));
    }
}
