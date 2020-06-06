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

import com.colin.games.werewolf.client.role.Roles;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDispatch;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * A cache to hold player status.
 */
public class PlayerCache {
    private PlayerCache(){
        throw new AssertionError();
    }
    private static List<Player> players = new ArrayList<>();
    private static boolean init = false;

    /**
     * Initializes the cache. <br>
     * This should not be called from application code.
     * @param fullMsg The message to initialize the cache with
     */
    public static void init(String fullMsg){
        players = Arrays.stream(fullMsg.split(";")).map(str -> str.split(":")).map(sArr -> new Player(sArr[0], Roles.makeNew(sArr[1]))).collect(Collectors.toList());
        MessageDispatch.register(lookup(Client.getCurrent().getName()).getRole().callbackName(),(ctx,msg) -> {
            Player p = lookup(Client.getCurrent().getName());
            if(p.isDead()){
                ctx.channel().write(new Message("next","empty"));
                ctx.channel().flush();
            }else{
                p.getRole().action(ctx,msg);
            }
        });
        init = true;
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,"You have been assigned role " + lookup(Client.getCurrent().getName()).getRole().toString()));
    }

    /**
     * Returns the list of players which are not dead.
     * @return The list of not-dead players
     */
    public static List<Player> notDead(){
        return players.stream().filter(p -> !p.isDead()).collect(Collectors.toList());
    }

    /**
     * Updates the cache with the supplied String. <br>
     * Warning: Care must be taken to synchronize the state between the server and the client
     * should you choose to use this method. <br>
     * You are strongly discouraged to use this from application code.
     * @param fullMsg The message to update the cache with
     */
    public static void update(String fullMsg){
        for(String s : fullMsg.split(";")){
            String[] arr = s.split(":");
            if(arr.length != 2){
                continue;
            }
            if(arr[1].equals("kill")){
                lookup(arr[0]).kill();
            }
        }
    }

    /**
     * Looks up a player by its name.
     * @param s The name of the player to search for
     * @return The player, if found
     * @throws NoSuchElementException If no player is found for this input String
     */
    public static Player lookup(String s) throws NoSuchElementException{
        return players.stream().filter(play -> play.getName().equals(s)).findFirst().orElseThrow(() -> new NoSuchElementException("No such player found for input string " + s + "."));
    }

    /**
     * Tests if the cache is initialized by the {@link com.colin.games.werewolf.client.PlayerCache#init init()} method.
     * @return Whether the cache is initialized
     */
    public static boolean isInitialized(){
        return init;
    }
}
