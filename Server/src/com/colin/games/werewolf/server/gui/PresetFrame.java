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

package com.colin.games.werewolf.server.gui;

import com.colin.games.werewolf.server.RoleList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresetFrame extends JFrame {
    private static final Map<JButton,String> lookup = new HashMap<>();
    public PresetFrame(int players,List<String> presets){
        super("Presets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        List<String> toDisplay = new ArrayList<>();
        for(String s : presets){
            Map<String,Integer> map = new HashMap<>();
            for(String ch : s.split(",")){
                if(map.containsKey(ch)){
                    map.put(ch,map.get(ch) + 1);
                }else{
                    map.put(ch,1);
                }
            }
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,Integer> ent : map.entrySet()){
                sb.append(ent.getValue()).append(" ").append(RoleList.getFromAbbreviation(ent.getKey())).append(',');
            }
            toDisplay.add(sb.deleteCharAt(sb.length() - 1).toString());
        }
        for(String s : toDisplay){
            JButton button = new JButton(s);
            lookup.put(button,s);
            add(button);
            button.addActionListener(ignored -> {
                dispose();
                String op = lookup.get(button);

            });
        }
        JButton custom = new JButton("Customize...");
        add(custom);
        custom.addActionListener(ignored -> {
            dispose();
            new CustomFrame(players);
        });
        pack();
        setVisible(true);
    }
}
