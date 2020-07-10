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
import com.colin.games.werewolf.client.ClientMain;
import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDispatch;
import com.colin.games.werewolf.common.modding.Mod;
import com.colin.games.werewolf.common.modding.ModLoader;
import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

/**
 * A GUI for choosing your name in the game.
 */
public class NameFrame extends JFrame {
    private final CyclicBarrier await = new CyclicBarrier(2);
    private boolean success = false;
    private String requested = "I'M_A_LITTLE_ERROR,_SHORT_AND_STOUT";
    private final Logger log = ClientMain.appendLog(LogManager.getFormatterLogger("Pre-Connection"));

    /**
     * Constructs a new NameFrame.
     */
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
            if(requested == null || requested.isEmpty()){
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Please enter a valid name!", "Name taken!", JOptionPane.WARNING_MESSAGE));
                return;
            }
            if(requested.contains("://:") || requested.contains(";") || requested.contains(",")){
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Name contains invalid characters!", "Name taken!", JOptionPane.WARNING_MESSAGE));
                return;
            }
            submit.setEnabled(false);
            log.info("Awaiting for connection to server!");
            Client.getCurrent().connectFuture().syncUninterruptibly();
            Channel chan = Client.getCurrent().getChannel();
            log.info("Asking for name " + requested);
            chan.write(new Message("query_name",requested));
            chan.flush();
            MessageDispatch.register("name_res",(ignore,res) -> {
                if(Boolean.parseBoolean(res.getContent())){
                    log.info("Name successfully set as " + requested);
                    success = true;
                }else{
                    log.info("Name " + requested + " is not available.");
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
            log.info("Querying if the server is full...");
            chan.write(new Message("full_query","empty"));
            chan.flush();
            MessageDispatch.register("is_full_res",(ignore,res)-> {
                if(Boolean.parseBoolean(res.getContent())){
                    log.info("Server is full! Exiting...");
                    success = false;
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Server is full. Please try again later", "Server Full!", JOptionPane.WARNING_MESSAGE));
                    try{
                        Thread.sleep(6000);
                    }catch(InterruptedException ie){
                        ie.printStackTrace();
                    }
                    System.exit(0);
                }else{
                    log.info("Server is not full!");
                }
                if(Environment.isModded()){
                    log.info("Asking server about mods...");
                    MessageDispatch.register("mod_response", (ctx, msg) -> {
                        log.debug("Mod response received with message " + msg.getContent());
                        try {
                            boolean result = Boolean.parseBoolean(msg.getContent());
                            if (result) {
                                log.info("The server has accepted this mod list.");
                            } else {
                                log.info("The server cannot accept this mod list!");
                                System.exit(0);
                            }
                        } catch (IllegalArgumentException iae) {
                            throw new AssertionError(iae);
                        }
                        dispose();
                        Client.getCurrent().getChannel().write(new Message("join_game",requested));
                        Client.getCurrent().getChannel().flush();
                        MessageDispatch.register("name_res",null);
                        MessageDispatch.register("is_full_res",null);
                        Client.getCurrent().setName(requested);
                        TabbedMainFrame chat = new TabbedMainFrame(requested);
                        MessageDispatch.register("chat",chat::displayMsg);
                    });
                    Client.getCurrent().getChannel().write(new Message("mod_query", ModLoader.getLoaded().stream().map(Mod::depsOnOtherSide).map(l -> String.join(";", l).strip()).collect(Collectors.joining(";"))));
                    Client.getCurrent().getChannel().flush();
                }else{
                    dispose();
                    Client.getCurrent().getChannel().write(new Message("join_game",requested));
                    Client.getCurrent().getChannel().flush();
                    MessageDispatch.register("name_res",null);
                    MessageDispatch.register("is_full_res",null);
                    Client.getCurrent().setName(requested);
                    TabbedMainFrame frame = new TabbedMainFrame(requested);
                    MessageDispatch.register("chat",frame::displayMsg);
                }
            });
        });
        panel.add(submit);
        add(panel);
        pack();
        setVisible(true);
    }

    /**
     * Gets the requested name.
     * @return The requested name
     */
    public String getName(){
        return requested;
    }
}
