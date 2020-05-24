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
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDispatch;
import io.netty.channel.Channel;

import javax.swing.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class NameFrame extends JFrame {
    private final CyclicBarrier await = new CyclicBarrier(2);
    private boolean success = false;
    private String requested = "I'M_A_LITTLE_ERROR,_SHORT_AND_STOUT";
    public NameFrame(){
        super("Choose your name");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        add(new JLabel("Welcome, player! Please input your screen name:"));
        JPanel panel = new JPanel();
        JTextField name = new JTextField(20);
        panel.add(name);
        JButton submit = new JButton("Submit");
        submit.addActionListener(ignored -> {
            requested = name.getText();
            if(requested == null || requested.isBlank()){
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Please enter a valid name!", "Name taken!", JOptionPane.WARNING_MESSAGE));
                return;
            }
            Channel chan = Client.getCurrent().getChannel();
            chan.write(new Message("query_name",requested));
            chan.flush();
            MessageDispatch.register("name_res",(ignore,res) -> {
                if(Boolean.parseBoolean(res.getContent())){
                    success = true;
                }else{
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Name is already taken. Please try another one!", "Name taken!", JOptionPane.WARNING_MESSAGE));
                }
                try {
                    await.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            try {
                await.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            await.reset();
            chan.write(new Message("full_query","empty"));
            chan.flush();
            MessageDispatch.register("is_full_res",(ignore,res)-> {
                if(Boolean.parseBoolean(res.getContent())){
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Server is full. Please try again later", "Server Full!", JOptionPane.WARNING_MESSAGE));
                }
                try{
                    await.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            try{
                await.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            await.reset();
            if(success){
                dispose();
                MessageDispatch.register("name_res",null);
                MessageDispatch.register("is_full_res",null);
                Client.getCurrent().setName(requested);
                ChatFrame chat = new ChatFrame(requested);
                MessageDispatch.register("chat",chat::displayMsg);
            }
        });
        panel.add(submit);
        add(panel);
        pack();
        setVisible(true);
    }
    public String getName(){
        return requested;
    }
}
