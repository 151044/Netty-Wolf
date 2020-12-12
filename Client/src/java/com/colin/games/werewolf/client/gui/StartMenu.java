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

import com.colin.games.werewolf.common.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The starting menu. The first GUI of the game.
 */
public class StartMenu extends JFrame {
    /**
     * Constructs a new StartMenu.
     */
    public StartMenu(){
        super("Start Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        CardLayout ref = new CardLayout();
        JPanel actual = new JPanel(ref);
        JPanel init = new JPanel();
        init.setLayout(new BoxLayout(init,BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Netty-Wolf");
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setFont(name.getFont().deriveFont(14.0f));
        init.add(name);
        JLabel subTitle = new JLabel("A werewolf game");
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        init.add(subTitle);
        JLabel by = new JLabel("Made By: 151044");
        by.setAlignmentX(Component.CENTER_ALIGNMENT);
        init.add(by);
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
        JButton connect = new JButton("Connect");
        connect.addActionListener(ignored -> {
            dispose();
            ConnectFrame conn = new ConnectFrame();
            SwingUtilities.invokeLater(conn::init);
        });
        buttons.add(connect);
        JButton about = new JButton("About");
        about.addActionListener(ignored -> ref.show(actual,"About"));
        buttons.add(about);
        if(Environment.isModded()){
            JButton mods = new JButton("Mods");
            buttons.add(mods);
            mods.addActionListener(ignored -> new ModListPane());
        }
        JButton settings = new JButton("Settings");
        settings.addActionListener(ignored -> ref.show(actual,"Settings"));
        buttons.add(settings);
        init.add(buttons);
        actual.add(init,"Start");
        actual.add(new AboutPane(),"About");
        actual.add(new SettingsPane(),"Settings");
        add(actual,BorderLayout.CENTER);
        JMenuBar mBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        mBar.add(menu);
        JMenuItem item = new JMenuItem("Back", KeyEvent.VK_B);
        item.addActionListener(ae -> ref.show(actual,"Start"));
        JMenuItem setMenu = new JMenuItem("Settings", KeyEvent.VK_S);
        setMenu.addActionListener((ae) -> ref.show(actual,"Settings"));
        menu.add(item);
        menu.add(setMenu);
        JMenu quitMenu = new JMenu("Quit");
        JMenuItem leave = new JMenuItem("Quit",KeyEvent.VK_Q);
        leave.addActionListener((ae) -> {
            int ret = JOptionPane.showConfirmDialog(null,"Do you really want to exit?","Exit?",JOptionPane.YES_NO_OPTION);
            if(ret == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        });
        quitMenu.add(leave);
        mBar.add(quitMenu);
        setJMenuBar(mBar);
        pack();
        ref.show(actual,"Start");
        setLocationRelativeTo(null);
        //When I get a 64x64 icon
        //setIconImage(ClientMain.getIcon());
        setVisible(true);
    }
}
