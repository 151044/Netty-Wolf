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

import com.colin.games.werewolf.client.ClientMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FreeFrame extends JFrame {
    private static final Logger log = ClientMain.appendLog(LogManager.getFormatterLogger("About"));
    public FreeFrame(){
        super("License");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();
        jta.append("Preamble:\n" +
                "This is not a license in the conventional sense. While a software license seeks to limit your, the user, of their rights, this license " +
                "aims to provide you with indisputable rights to modify, to distribute, and to share, given that the resulting product also follows this license" +
                "in order to preserve the rights that are given to you by this document.\n\n\n");
        log.info("Loading GPL...");
        try {
            final URI uri = getClass().getResource("/resources").toURI();
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            FileSystem zip = FileSystems.newFileSystem(uri, env);
            jta.append(String.join("\n",Files.readAllLines(Path.of(ClassLoader.getSystemResource("resources/GPL.txt").toURI()))));
        } catch (IOException | URISyntaxException e) {
            log.info("Failed to load GPL!");
            throw new RuntimeException(e);
        }
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        JScrollPane textScroll = new JScrollPane(jta);
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(textScroll);
        add(textScroll,BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}
