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

public class WerewolfPane extends JPanel {
    private final Map<String,String> choices = new HashMap<>();
    private final Map<String,JLabel> map = new HashMap<>();
    private final JPanel otherP = new JPanel();
    private final JButton ok = new JButton("OK");
    private static boolean isInitialized = false;
    public WerewolfPane(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel you = new JPanel();
        you.setLayout(new BoxLayout(you,BoxLayout.X_AXIS));
        you.add(new JLabel("Select a person to kill:"));
        JComboBox<Player> players = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        players.addItemListener(evt -> {
            if(evt.getStateChange() == ItemEvent.SELECTED){
                Player select = (Player) evt.getItem();
                String send = select.getName();
                Client.getCurrent().getChannel().write(new Message("wolf_init",Client.getCurrent().getName() + ":" + send));
                Client.getCurrent().getChannel().flush();
                ok.setEnabled(true);
            }
        });
        you.add(players);
        add(you);
        otherP.setLayout(new BoxLayout(otherP,BoxLayout.Y_AXIS));
        add(otherP);
        ok.setEnabled(false);
        ok.addActionListener(ae -> {
            Channel chan = Client.getCurrent().getChannel();
            chan.write(new Message("werewolf_kill",choices.values().stream().findAny().orElseThrow()));
            chan.flush();
            chan.write(new Message("next","empty"));
            chan.flush();
        });
        ok.setEnabled(false);
        add(ok);
        MessageDispatch.register("wolf_init",(ctx, msg) -> {
            String[] split = msg.getContent().split(":");
            setDisplay(split[0],split[1]);
            choices.put(split[0],split[1]);
            ok.setEnabled(new HashSet<>(choices.values()).size() == 1);
        });
        MessageDispatch.register("werewolf_term",(ctx,msg) -> setVisible(false));
    }
    /**
     * Sets the decision of a player.
     * @param player The player to set the decision
     * @param decide The player that the werewolf has decided to kill.
     */
    public void setDisplay(String player,String decide){
        SwingUtilities.invokeLater(() -> map.get(player).setText(player + ": " + decide));
    }
    public void updateFrame(List<String> toDiff){
        map.entrySet().removeIf(ent -> !toDiff.contains(ent.getKey()));
    }
    public void initLabels(List<String> others){
        if(isInitialized){
            throw new IllegalStateException("Re-init of labels in werewolf.");
        }
        for(String s : others){
            JLabel label = new JLabel(s + ": ");
            otherP.add(label);
            map.put(s,label);
        }
        if(!(others.size() == 1)){
            ok.setEnabled(false);
        }
        isInitialized = true;
    }
    public boolean hasInitialized(){
        return isInitialized;
    }
}
