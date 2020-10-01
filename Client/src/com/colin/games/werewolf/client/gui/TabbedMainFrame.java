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

import com.colin.games.werewolf.common.message.Message;
import io.netty.channel.ChannelHandlerContext;

import javax.swing.*;
import java.awt.*;

/**
 * The main game frame.
 */
public class TabbedMainFrame extends JFrame {
    private static TabbedMainFrame current = null;
    private final JTabbedPane tabs;
    private final ChatPane chat;

    /**
     * Creates a new game frame.
     * @param name The name of the player
     */
    public TabbedMainFrame(String name){
        super("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        chat = new ChatPane(name);
        tabs.addTab("Chat",chat);
        tabs.addTab("Diary",new Diary());
        add(tabs);
        pack();
        setVisible(true);
    }

    /**
     * An adaptor for displaying messages to the chat.
     * @param ignored The channel handler context, ignored but included for compat purposes with the callbacks
     * @param message The message to display to the chat pane
     */
    public void displayMsg(ChannelHandlerContext ignored, Message message) {
        chat.displayMsg(message);
    }
    public static TabbedMainFrame getCurrent(){
        if(current == null){
            throw new IllegalStateException("Current TabbedMainFrame is null!");
        }
        return current;
    }

    /**
     * Sets the current main frame.
     * @param toSet The frame to set
     */
    public static void setCurrent(TabbedMainFrame toSet){
        current = toSet;
    }

    /**
     * Adds a tab to the main pane.
     * @param title Title of the pane
     * @param toAdd The panel to add
     */
    public void addTab(String title,JPanel toAdd){
        SwingUtilities.invokeLater(() -> {
            tabs.addTab(title, toAdd);
        });
    }
}
