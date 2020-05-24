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

import com.colin.games.werewolf.server.Server;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InitFrame extends JFrame {
    private int ports = -1;
    private int players = -1;
    private boolean noLim = true;
    public InitFrame(){
        super("Server Options");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel portP = new JPanel();
        portP.add(new JLabel("Port:"));
        JTextField port = new JTextField(6);
        portP.add(port);
        JButton submit = new JButton("Start!");
        portP.add(submit);
        submit.addActionListener((ignored) -> {
            try {
                int initial = Integer.parseInt(port.getText());
                if(initial < 1){
                    JOptionPane.showMessageDialog(null,"Invalid number entered!","Please try again.",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                ports = initial;
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Invalid number entered!","Please try again.",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dispose();
            Server server = new Server(ports,players);
            Server.setInstance(server);
            server.run();
        });
        port.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    submit.doClick();
                }
            }
        });
        submit.setEnabled(false);
        add(portP);
        JPanel opts = new JPanel();
        opts.setLayout(new BoxLayout(opts,BoxLayout.X_AXIS));
        opts.add(new JLabel("No. of players:"));
        JTextField numPlays = new JTextField(3);
        opts.add(numPlays);
        JButton numPlaysB = new JButton("Submit");
        opts.add(numPlaysB);
        numPlaysB.addActionListener(ignored -> {
            try{
                players = Integer.parseInt(numPlays.getText());
                if(players < 5 && !noLim){
                    JOptionPane.showMessageDialog(null,"Invalid number of players entered!\n The minimum is 5.","Please try again.",JOptionPane.INFORMATION_MESSAGE);
                    players = -1;
                    submit.setEnabled(false);
                    return;
                }
                submit.setEnabled(true);
            }catch(NumberFormatException nfe){
                submit.setEnabled(false);
                JOptionPane.showMessageDialog(null,"Invalid number of players entered!","Please try again.",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(opts);
        pack();
        setVisible(true);
    }
}
