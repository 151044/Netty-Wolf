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
import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDispatch;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * The pane for chat messages.
 */
public class ChatPane extends JPanel {
    private final PrintStream ps;
    private final JTextArea textArea;

    /**
     * Creates a new ChatPane with the specified player name.
     * @param name The name of the player playing the game
     */
    public ChatPane(String name){
        textArea = new JTextArea();
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textScroll = new JScrollPane(textArea);
        setLayout(new BorderLayout());
        pane.add(textScroll);
        add(pane,BorderLayout.CENTER);
        ps = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                SwingUtilities.invokeLater(() -> textArea.append(String.valueOf((char) b)));
                //not localizing
            }

            @Override
            public void write(byte[] b) {
                if (b == null) {
                    throw new NullPointerException();
                }
                SwingUtilities.invokeLater(() -> textArea.append(new String(b, CharsetUtil.UTF_8)));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                if (b == null) {
                    throw new NullPointerException();
                } else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
                    throw new IndexOutOfBoundsException();
                } else if (len == 0) {
                    return;
                }
                String actual = new String(Arrays.copyOfRange(b, off, off + len), CharsetUtil.UTF_8);
                SwingUtilities.invokeLater(() -> textArea.append(actual));
            }
        });
        JPanel sendP = new JPanel();
        JTextField msg = new JTextField(80);
        JButton send = new JButton("Send");
        sendP.add(msg);
        sendP.add(send);
        add(sendP, BorderLayout.PAGE_END);
        msg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    send.doClick();
                }
            }
        });
        send.setMnemonic(KeyEvent.VK_ENTER);
        send.addActionListener(ae -> {
            if(PlayerCache.isInitialized()) {
                Player lookup = PlayerCache.lookup(Client.getCurrent().getName());
                if (!(lookup == null)) {
                    if (PlayerCache.lookup(Client.getCurrent().getName()).isDead()) {
                        send.setEnabled(false);
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "You are dead and cannot speak!"));
                        msg.setText("");
                        return;
                    }
                }
            }
            if(msg.getText().contains("://:")){
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Message cannot be entered!", "Invalid message!", JOptionPane.WARNING_MESSAGE));
                return;
            }else if(msg.getText().equals("")){
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Empty message!", "Invalid message!", JOptionPane.WARNING_MESSAGE));
                return;
            }
            String output = "[" + name + "]: " + msg.getText();
            Channel chan = Client.getCurrent().getChannel();
            chan.write(new Message("chat",output));
            chan.flush();
            msg.setText("");
        });
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        MessageDispatch.register("night",(ctx, m) -> send.setEnabled(false));
        MessageDispatch.register("day",(ctx,m) -> send.setEnabled(true));
    }

    /**
     * Displays a message to the pane.
     * @param message The message to write to the pane
     */
    public void displayMsg(Message message) {
        ps.println(message.getContent());
        ps.flush();
    }
}
