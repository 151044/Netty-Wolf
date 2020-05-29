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

import com.colin.games.werewolf.client.Client;
import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class VotingFrame extends JFrame {
    private final Map<String,JLabel> map = new HashMap<>();
    public VotingFrame(){
        super("Vote!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel voteP = new JPanel();
        voteP.setLayout(new BoxLayout(voteP,BoxLayout.X_AXIS));
        voteP.add(new JLabel("Select who to vote for: "));
        JComboBox<Player> players = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        voteP.add(players);
        players.addItemListener(ae -> {
            if(ae.getStateChange() == ItemEvent.SELECTED) {
                Client.getCurrent().writeAndFlush(new Message("vote_init", ((Player) players.getSelectedItem()).getName()));
            }
        });
        JButton submit = new JButton("Submit");
        voteP.add(submit);
        submit.addActionListener(ae -> Client.getCurrent().writeAndFlush(new Message("vote_final",((Player) players.getSelectedItem()).getName())));
        add(voteP);
        JPanel otherVotes = new JPanel();
        otherVotes.setLayout(new BoxLayout(otherVotes,BoxLayout.Y_AXIS));
        for(Player p : PlayerCache.notDead()){
            JLabel label = new JLabel(p.getName() + ": ");
            map.put(p.getName(),label);
            otherVotes.add(label);
        }
        add(otherVotes);
        pack();
        setVisible(true);
    }
    public void setSelection(String player,String choice){
        map.get(player).setText(player + " : " + choice);
    }
}
