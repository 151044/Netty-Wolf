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

import com.colin.games.werewolf.server.RoleDispatch;
import com.colin.games.werewolf.server.RoleList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFrame extends JFrame {
    private final Map<String,JTextField> textMap = new HashMap<>();
    private static final List<String> enforceOne = new ArrayList<>();
    static{
        enforceOne.add("Witch");
        enforceOne.add("Seer");
        enforceOne.add("Guard");
        enforceOne.add("Hunter");
    }
    public CustomFrame(int players){
        super("Customize Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        for(String s : RoleList.getRoles()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
            JLabel name = new JLabel(s + ":");
            panel.add(name);
            JTextField field = new JTextField(3);
            panel.add(field);
            textMap.put(s,field);
            add(panel);
        }
        JButton submit = new JButton("Submit");
        submit.addActionListener(ignored -> {
            dispose();
            if(textMap.values().stream().mapToInt(tf -> {
                try {
                    int check = Integer.parseInt(tf.getText());
                    if(check < 0){
                        check = 0;
                    }
                    return check;
                }catch(NumberFormatException nfe){
                    return 0;
                }
            }).sum() != players){
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,"Invalid number of players!","Invalid options!",JOptionPane.WARNING_MESSAGE));
            }
            Map<String,Integer> map = new HashMap<>();
            textMap.forEach((key, value) -> {
                int res;
                try {
                    res = Integer.parseInt(value.getText());
                } catch (NumberFormatException nfe) {
                    res = 0;
                }
                map.put(key, res);
            });
            for(String s : enforceOne){
                if(map.getOrDefault(s,1) > 1){
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,"Invalid number for role " + s + ", must be 1.","Wrong number of roles!",JOptionPane.WARNING_MESSAGE));
                    return;
                }
            }
            RoleDispatch.handle(map);
        });
        add(submit);
        pack();
        setVisible(true);
    }
}
