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

package com.colin.games.werewolf.common.utils;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * The new frame for outputting to the screen.
 * @author Colin 
 */
public class OutputFrame extends JFrame {
    private boolean echoToSysOut = false;
    private final JTextArea textArea;
    private final PrintStream ps;

    /**
     * Constructs a new output frame.
     */
    public OutputFrame() {
        this("Output Frame");
    }

    /**
     * Constructs a new output frame instance with the specified name.
     *
     * @param name The name of the frame
     */
    public OutputFrame(String name) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textScroll = new JScrollPane(textArea);
        setLayout(new BorderLayout());
        pane.add(textScroll);
        add(pane);
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
                SwingUtilities.invokeLater(() -> textArea.append(new String(b, StandardCharsets.UTF_8)));
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
                String actual = new String(Arrays.copyOfRange(b, off, off + len), StandardCharsets.UTF_8);
                SwingUtilities.invokeLater(() -> textArea.append(actual));
            }
        });
        pack();
        setVisible(true);
    }

    /**
     * Convenience method to print a line to the output frame.
     *
     * @param s The string to print
     */
    public void println(String s) {
        if (echoToSysOut) {
            System.out.println(s);
        }
        ps.println(s);
        ps.flush();
    }

    /**
     * Returns the print stream of this instance.
     *
     * @return The print stream of this instance
     */
    public PrintStream getPrintStream() {
        return ps;
    }

    /**
     * Returns the underlying output frame.
     *
     * @return The underlying text area of this instance
     */
    public JTextArea getTextArea() {
        return textArea;
    }


    /**
     * Toggles printing to System.out.
     *
     * @param toggle The toggle for printing out
     */
    public void echoToSysOut(boolean toggle) {
        echoToSysOut = toggle;
    }

    /**
     * Returns a consumer for accepting objects and printing them with its toString() method.
     *
     * @return A consumer to print objects
     */
    public Consumer<Object> getPrintConsumer() {
        return (t) -> ps.println(t.toString());
    }

    /**
     * Prints a string to this instance's output frame.
     * @param s The string to print
     */
    public void print(String s) {
        if (echoToSysOut) {
            System.out.print(s);
        }
        ps.print(s);
        ps.flush();
    }

    /**
     * Closes the output stream. Further inputs will result in an exception.
     */
    public void close() {
        ps.close();
    }
}
