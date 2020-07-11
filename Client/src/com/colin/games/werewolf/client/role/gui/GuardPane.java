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
import com.colin.games.werewolf.client.role.Guard;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import java.util.Vector;
import java.util.stream.Collectors;

public class GuardPane extends JPanel {
    private JComboBox<Player> players;
    public GuardPane(Guard context){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel choiceP = new JPanel();
        choiceP.setLayout(new BoxLayout(choiceP,BoxLayout.X_AXIS));
        choiceP.add(new JLabel("Choose a person to guard: "));
        players = new JComboBox<>(new Vector<>(PlayerCache.notDead().stream().filter(pla -> (pla != null) && !pla.equals(context.lastSaved())).collect(Collectors.toList())));
        choiceP.add(players);
        JButton submit = new JButton("Protect");
        choiceP.add(submit);
        submit.addActionListener((ignored) -> SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,"You protected " + ((Player) players.getSelectedItem()).getName() + ".","Information",JOptionPane.INFORMATION_MESSAGE);
            submit.setEnabled(false);
            Client.getCurrent().writeAndFlush(new Message("guard",((Player) players.getSelectedItem()).getName()));
            context.setSaved((Player) players.getSelectedItem());
        }));
        add(choiceP);
        JButton pass = new JButton("Pass");
        add(pass);
        pass.addActionListener((ignored) -> {
            setVisible(false);
            Client.getCurrent().writeAndFlush(new Message("next","empty"));
        });
    }
    public void updateGUI(Guard context){
        players = new JComboBox<>(new Vector<>(PlayerCache.notDead().stream().filter(pla -> (pla != null) && !pla.equals(context.lastSaved())).collect(Collectors.toList())));
    }
}
