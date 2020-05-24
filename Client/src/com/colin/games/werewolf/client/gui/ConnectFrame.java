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

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectFrame extends JFrame {
    public ConnectFrame(){
        super("Connect to a Server");
    }
    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
        pane.add(new JLabel("IP Address:"));
        JTextField ip = new JTextField(15);
        pane.add(ip);
        pane.add(new JLabel("Port:"));
        JTextField port = new JTextField(6);
        pane.add(port);
        JButton submit = new JButton("Connect!");
        pane.add(submit);
        submit.addActionListener(ae -> {
            try {
                int handler = Integer.parseInt(port.getText());
                InetAddress addr = InetAddress.getByName(ip.getText());
                Client cli = new Client(addr,handler);
                Client.setCurrent(cli);
                cli.run();
                new NameFrame();
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Invalid port number entered!","Please try again.",JOptionPane.INFORMATION_MESSAGE);
            }catch(UnknownHostException uhe){
                JOptionPane.showMessageDialog(null,"Invalid IP entered!","Please try again.",JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        });
        add(pane);
        pack();
        setVisible(true);
    }
}
