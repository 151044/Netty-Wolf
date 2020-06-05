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

package com.colin.games.werewolf.client.role.gui;

import com.colin.games.werewolf.client.Client;
import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import java.util.Vector;

/**
 * Shows the window for the hunter to take action.
 * @see com.colin.games.werewolf.client.role.Hunter Hunter
 */
public class HunterFrame extends JFrame {
    /**
     * Constructs a new HunterFrame.
     */
    public HunterFrame() {
        super("Revenge!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("You have been killed!"));
        JPanel choiceP = new JPanel();
        choiceP.setLayout(new BoxLayout(choiceP, BoxLayout.X_AXIS));
        choiceP.add(new JLabel("Choose a person to take revenge against: "));
        JComboBox<Player> players = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        choiceP.add(players);
        JButton submit = new JButton("Kill");
        choiceP.add(submit);
        submit.addActionListener((ignored) -> SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, ((Player) players.getSelectedItem()).getName() + " is now killed!" , "Information", JOptionPane.INFORMATION_MESSAGE);
            Client.getCurrent().writeAndFlush(new Message("hunter_kill",((Player) players.getSelectedItem()).getName()));
            submit.setEnabled(false);
        }));
        add(choiceP);
        JButton pass = new JButton("Pass");
        add(pass);
        pass.addActionListener((ignored) -> {
            dispose();
            Client.getCurrent().writeAndFlush(new Message("next", "empty"));
        });
        pack();
        setVisible(true);
    }
}
