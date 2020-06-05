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
import com.colin.games.werewolf.client.role.Witch;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import java.util.Vector;

/**
 * Shows the window for the witch to take action.
 * @see com.colin.games.werewolf.client.role.Witch Witch
 */
public class WitchFrame extends JFrame {
    public WitchFrame(String dead, Witch context){
        super("Your turn!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel save = new JPanel();
        save.setLayout(new BoxLayout(save,BoxLayout.X_AXIS));
        save.add(new JLabel("In this round, " + dead + " has been killed."));
        JButton heal = new JButton("Save");
        if(context.isHealUsed()){
            heal.setEnabled(false);
        }
        save.add(heal);
        heal.addActionListener((ignored) -> {
            Client.getCurrent().writeAndFlush(new Message("witch_heal","empty"));
            context.setHealStatus(true);
            heal.setEnabled(false);
        });
        add(save);
        JPanel kill = new JPanel();
        JComboBox<Player> choice = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        JButton submitKill = new JButton("Kill");
        if (context.isKillUsed()) {
            submitKill.setEnabled(false);
        }
        kill.add(choice);
        kill.add(submitKill);
        submitKill.addActionListener(ignored -> {
            Player p = (Player) choice.getSelectedItem();
            context.setKillStatus(true);
            submitKill.setEnabled(false);
            Client.getCurrent().writeAndFlush(new Message("witch_kill",p.getName()));
        });
        JButton pass = new JButton("Pass");
        pass.addActionListener(ignored -> {
            Client.getCurrent().writeAndFlush(new Message("next","empty"));
            dispose();
        });
        add(kill);
        add(pass);
        pack();
        setVisible(true);
    }
}
