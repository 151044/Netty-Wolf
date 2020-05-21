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
                cli.run();
                Client.setCurrent(cli);
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
