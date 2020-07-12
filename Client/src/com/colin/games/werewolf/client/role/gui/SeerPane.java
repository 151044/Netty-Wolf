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

public class SeerPane extends JPanel {
    public SeerPane(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel choiceP = new JPanel();
        choiceP.setLayout(new BoxLayout(choiceP,BoxLayout.X_AXIS));
        choiceP.add(new JLabel("Choose a person to check: "));
        JComboBox<Player> players = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        choiceP.add(players);
        JButton submit = new JButton("Check");
        choiceP.add(submit);
        submit.addActionListener((ignored) -> SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,((Player) players.getSelectedItem()).getName() + (((Player) players.getSelectedItem()).getRole().getGroup().isGood() ? " has an aura of normalcy around them." : " has an evil aura around them!"),"Information",JOptionPane.INFORMATION_MESSAGE);
            submit.setEnabled(false);
        }));
        submit.setPreferredSize(submit.getPreferredSize());
        add(choiceP);
        JButton pass = new JButton("Pass");
        add(pass);
        pass.addActionListener((ignored) -> {
            setVisible(false);
            Client.getCurrent().writeAndFlush(new Message("next","empty"));
        });
    }
}
