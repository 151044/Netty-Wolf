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
import java.util.List;
import java.util.Vector;

public class WitchPane extends JPanel {
    private final JComboBox<String> saveChoice;
    private final JComboBox<Player> choice;
    private final Witch context;
    private final JButton heal;
    private final JButton submitKill;
    private final JLabel text = new JLabel();

    public WitchPane(Witch context){
        this.context = context;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel save = new JPanel();
        save.setLayout(new BoxLayout(save,BoxLayout.X_AXIS));
        save.add(text);
        heal = new JButton("Save");
        save.add(heal);
        saveChoice = new JComboBox<>();
        saveChoice.setPreferredSize(saveChoice.getPreferredSize());
        heal.addActionListener((ignored) -> {
            Client.getCurrent().writeAndFlush(new Message("witch_heal",((Player) saveChoice.getSelectedItem()).getName()));
            context.setHealStatus(true);
            heal.setEnabled(false);
        });
        add(save);
        JPanel kill = new JPanel();
        choice = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        choice.setPreferredSize(choice.getPreferredSize());
        submitKill = new JButton("Kill");
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
            setVisible(false);
            Client.getCurrent().writeAndFlush(new Message("next","empty"));
        });
        add(kill);
        add(pass);
    }
    public void update(List<String> toUpdate){
        text.setText("In this round, " + String.join(" and ",toUpdate) + " has been killed");
        saveChoice.removeAllItems();
        toUpdate.forEach(saveChoice::addItem);
        choice.removeAllItems();
        PlayerCache.notDead().forEach(choice::addItem);
        if (context.isKillUsed()) {
            submitKill.setEnabled(false);
        }
        if(context.isHealUsed()){
            heal.setEnabled(false);
        }
    }
}
