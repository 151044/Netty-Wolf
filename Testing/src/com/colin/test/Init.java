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
import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.client.role.*;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Init {
    private Init(){
        throw new AssertionError();
    }
    public static void init(){
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        try {
            Client.setCurrent(new Client(InetAddress.getByName("127.0.0.1"),18823));
            Client.getCurrent().setName("151044");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Roles.register("Villager", Villager::new);
        Roles.register("Seer", Seer::new);
        Roles.register("Werewolf", Werewolf::new);
        Roles.register("Guard", Guard::new);
        Roles.register("Hunter",Hunter::new);
        PlayerCache.init("151044:Villager;151044a:Seer;Five-Nine:Werewolf;XDGUY:Guard;Eric...:Hunter");
    }
}
