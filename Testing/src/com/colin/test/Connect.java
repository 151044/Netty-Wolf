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

import com.colin.games.werewolf.client.Client;
import com.colin.games.werewolf.client.gui.NameFrame;
import com.colin.games.werewolf.common.Environment;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Connect {
    public static void main(String[] args) throws UnknownHostException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Environment.setSide(Environment.Side.SERVER);
        Client cli = new Client(InetAddress.getByName("61.18.35.8"),18823);
        Client.setCurrent(cli);
        cli.run();
        new NameFrame();
    }
}
