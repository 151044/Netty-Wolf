package com.colin.games.werewolf.server.gui;

import com.colin.games.werewolf.server.Server;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InitFrame extends JFrame {
    private int ports = -1;
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
            new Server(ports).run();
        });
        port.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    submit.doClick();
                }
            }
        });
        add(portP);
        pack();
        setVisible(true);
    }
}
