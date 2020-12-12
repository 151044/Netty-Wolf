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

package com.colin.test;

import com.colin.games.werewolf.client.gui.ChatPane;
import com.colin.games.werewolf.client.gui.DiaryPane;
import com.colin.games.werewolf.client.gui.MainFrame;
import com.colin.games.werewolf.common.message.Message;
import io.netty.channel.ChannelHandlerContext;

import javax.swing.*;
import java.awt.*;

public class TabbedMainFrameMockup extends JFrame {
    private static MainFrame current = null;
    private final JTabbedPane tabs;
    private final ChatPane chat;
    public TabbedMainFrameMockup(String name){
        super("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        chat = new ChatPane(name);
        tabs.addTab("Chat",chat);
        tabs.addTab("Diary",new DiaryPane());
        addTab("Hello",new DiaryPane());
        add(tabs);
        pack();
        setVisible(true);
    }
    public void displayMsg(ChannelHandlerContext ignored, Message message) {
        chat.displayMsg(message);
    }
    public static MainFrame getCurrent(){
        if(current == null){
            throw new IllegalStateException("Current TabbedMainFrame is null!");
        }
        return current;
    }
    public static void setCurrent(MainFrame toSet){
        current = toSet;
    }
    public void addTab(String title,JPanel toAdd){
        tabs.addTab(title, toAdd);
    }
}
