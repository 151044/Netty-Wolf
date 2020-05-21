package com.colin.games.werewolf.client.role.gui;

import com.colin.games.werewolf.client.Client;
import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.client.role.Witch;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import java.util.Vector;

public class WitchFrame extends JFrame {
    public WitchFrame(String dead, Witch context){
        super("Your turn!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel save = new JPanel();
        save.setLayout(new BoxLayout(save,BoxLayout.X_AXIS));
        save.add(new JLabel("In this round, " + dead + " has been killed."));
        JButton heal = new JButton("Save");
        if(context.isHealUsed()){
            heal.setEnabled(false);
        }
        save.add(heal);
        heal.addActionListener((ignored) -> {
            Client.getCurrent().writeAndFlush(new Message("witch_heal",""));
            context.setHealStatus(true);
            heal.setEnabled(false);
        });
        add(save);
        JPanel kill = new JPanel();
        JComboBox<Player> choice = new JComboBox<>(new Vector<>(PlayerCache.notDead()));
        JButton submitKill = new JButton("Kill");
        if (context.isKillUsed()) {
            submitKill.setEnabled(false);
        }
        kill.add(choice);
        kill.add(submitKill);
        submitKill.addActionListener(ignored -> {
            Player p = (Player) choice.getSelectedItem();
            Client.getCurrent().writeAndFlush(new Message("witch_kill",p.getName()));
        });
        JButton pass = new JButton("Pass");
        pass.addActionListener(ignored -> {
            Client.getCurrent().writeAndFlush(new Message("next",""));
            dispose();
        });
        add(kill);
        add(pass);
        pack();
        setVisible(true);
    }
}
