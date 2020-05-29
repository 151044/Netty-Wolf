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
import com.colin.games.werewolf.common.message.MessageDispatch;
import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.*;

public class WerewolfFrame extends JFrame {
    private List<String> choices = new ArrayList<>();
    private final Map<String,JLabel> map = new HashMap<>();
    public WerewolfFrame(List<String> others){
        super("Your turn!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel you = new JPanel();
        you.setLayout(new BoxLayout(you,BoxLayout.X_AXIS));
        you.add(new JLabel("Select a person to kill:"));
        JComboBox<Player> players = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        players.addItemListener(evt -> {
            if(evt.getStateChange() == ItemEvent.SELECTED){
                Player select = (Player) evt.getItem();
                String send = select.getName();
                Client.getCurrent().getChannel().write(new Message("wolf_init",send));
                Client.getCurrent().getChannel().flush();
            }
        });
        you.add(players);
        add(you);
        JPanel otherP = new JPanel();
        otherP.setLayout(new BoxLayout(otherP,BoxLayout.Y_AXIS));
        for(String s : others){
            JLabel label = new JLabel(s + ": ");
            otherP.add(label);
            map.put(s,label);
        }
        add(otherP);
        JButton ok = new JButton("OK");
        if(!(others.size() == 0)){
            ok.setEnabled(false);
        }
        ok.addActionListener(ae -> {
            Channel chan = Client.getCurrent().getChannel();
            chan.write(new Message("werewolf_kill",choices.get(0)));
            chan.flush();
            chan.write(new Message("next","empty"));
            chan.flush();
            dispose();
        });
        add(ok);
        pack();
        setVisible(true);
        MessageDispatch.register("wolf_init",(ctx,msg) -> {
            String[] split = msg.getContent().split(",");
            setDisplay(split[0],split[1]);
            choices.add(split[1]);
            String comp = choices.get(0);
            for(String s : choices){
                if(!s.equals(comp)){
                    ok.setEnabled(false);
                    return;
                }
            }
            ok.setEnabled(true);
        });
        MessageDispatch.register("werewolf_term",(ctx,msg) -> {
            dispose();
        });
    }
    public void setDisplay(String player,String decide){
        SwingUtilities.invokeLater(() -> map.get(player).setText(player + ": " + decide));
    }
}
