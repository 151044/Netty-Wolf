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

package com.colin.games.werewolf.client;

import com.colin.games.werewolf.client.gui.ConnectFrame;
import com.colin.games.werewolf.client.gui.ExceptionFrame;
import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ClientMain {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Environment.setSide(Environment.Side.CLIENT);
        Thread.setDefaultUncaughtExceptionHandler((t,ex) -> {
            ex.printStackTrace();
            if(!(ex instanceof Exception)){
                System.exit(2);
            }
            new ExceptionFrame((Exception) ex,t);
        });
        ConnectFrame cf = new ConnectFrame();
        SwingUtilities.invokeLater(cf::init);
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            Client.getCurrent().getChannel().write(new Message("disconnect",Client.getCurrent().getName()));
            Client.getCurrent().getChannel().flush();
         }));
    }
}
