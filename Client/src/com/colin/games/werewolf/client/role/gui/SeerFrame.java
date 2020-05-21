package com.colin.games.werewolf.client.role.gui;

import com.colin.games.werewolf.client.Client;
import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import java.util.Vector;

public class SeerFrame extends JFrame {
    public SeerFrame(){
        super("Your turn!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel choiceP = new JPanel();
        choiceP.setLayout(new BoxLayout(choiceP,BoxLayout.X_AXIS));
        choiceP.add(new JLabel("Choose a person to check: "));
        JComboBox<Player> players = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        choiceP.add(players);
        JButton submit = new JButton("Check");
        choiceP.add(submit);
        submit.addActionListener((ignored) -> SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,"The player " + ((Player) players.getSelectedItem()).getName() + " is " + (((Player) players.getSelectedItem()).getRole().isGood() ? "good." : "bad."),"Information",JOptionPane.INFORMATION_MESSAGE);
            submit.setEnabled(false);
        }));
        add(choiceP);
        JButton pass = new JButton("Pass");
        add(pass);
        pass.addActionListener((ignored) -> {
            dispose();
            Client.getCurrent().writeAndFlush(new Message("next",""));
        });
        pack();
        setVisible(true);
    }
}
