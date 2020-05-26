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

import com.colin.games.werewolf.common.Environment;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExceptionFrame extends JFrame {
    public ExceptionFrame(Exception ex,Thread t){
        super("Oops!");
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Something went wrong. Please report this."));
        JTextArea jta = new JTextArea();
        jta.append(getMsg(ex,t));
        add(jta);
        JPanel end = new JPanel();
        JButton write = new JButton("Export");
        JButton exit = new JButton("Exit");
        end.add(write);
        end.add(exit);
        write.addActionListener(ae -> {
            try {
                //The commented lines are for Java 8
                /*List<String> temp = new ArrayList<>();
                temp.add(getMsg(ex,t));*/
                Files.write(Environment.workingDir().resolve("netty-wolf-exception-" + LocalDateTime.now().toString().replace(':','_') + ".txt"),List.of(getMsg(ex,t)/*temp*/));
            } catch (IOException e) {
                System.exit(1);
            }
        });
        exit.addActionListener(ae -> System.exit(1));
        add(end);
        pack();
        setVisible(true);
    }
    private static String getMsg(Exception ex,Thread t){
        String base = ex.toString() + " at Thread " + t.getName();
        return base.concat("\n").concat(Arrays.stream(ex.getStackTrace()).map(ste -> "at " + ste.toString()).collect(Collectors.joining("\n")));
    }
}
