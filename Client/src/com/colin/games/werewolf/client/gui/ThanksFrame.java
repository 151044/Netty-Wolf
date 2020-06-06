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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThanksFrame extends JFrame {
    private static final Map<String,String> dedication = new HashMap<>();
    static{
        dedication.put("Lio","Lio, thank you for inspiring me to make this game.");
        dedication.put("HYWong","HYWong, thank you for your support and being an incredible tester.");
        dedication.put("Eric","Eric, thank you for donating your name for the cause!");
        dedication.put("XDGUY","To XDGUY: So long, and thanks for all the memes. And the time spent helping me debug. And the new ideas given.");
        dedication.put("You","Dear player, thank you for supporting me!");
    }
    public ThanksFrame(){
        super("Thank you!");
        java.util.List<String> list = new ArrayList<>(dedication.keySet());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        DefaultListModel<String> lm = new DefaultListModel<>();
        list.forEach(lm::addElement);
        JList<String> show = new JList<>(lm);
        show.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        show.setLayoutOrientation(JList.VERTICAL);
        show.setModel(lm);
        JButton info = new JButton("Show info");
        info.setMaximumSize(info.getPreferredSize());
        info.addActionListener((ignored) ->{
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,dedication.get(show.getSelectedValue()),"Thank you!",JOptionPane.PLAIN_MESSAGE));
        });
        show.addListSelectionListener((lse) ->{
            if(!lse.getValueIsAdjusting()){
                if(show.getSelectedIndex() == -1){
                    info.setEnabled(false);
                }else {
                    info.setEnabled(true);
                }
            }
        });
        JScrollPane jsp = new JScrollPane(show);
        jsp.setPreferredSize(jsp.getPreferredSize());
        JButton back = new JButton("Back");
        back.addActionListener((ignored) ->{
            dispose();
        });
        info.setEnabled(false);
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(info);
        buttons.add(back);
        add(jsp);
        add(buttons);
        pack();
        setVisible(true);
    }
}
