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

package com.colin.games.werewolf.server;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The pool of connected clients.
 */
public class Connections {
    private Connections(){
        throw new AssertionError();
    }
    private static final Map<String,Channel> channelMap = new HashMap<>();

    /**
     * Adds a channel to the pool.
     * @param s The name of the connection
     * @param ch The channel of the connection
     */
    public static void add(String s, Channel ch){
        channelMap.put(s,ch);
    }

    /**
     * Checks if a client of the given name exists.
     * @param s The name to check
     * @return True if the name exists in the pool, false otherwise
     */
    public static boolean has(String s){
        return channelMap.containsKey(s);
    }

    /**
     * Gets the list of channels which are currently active.
     * @return A list of active channels
     */
    public static List<Channel> openChannels(){
        return new ArrayList<>(channelMap.values());
    }

    /**
     * Removes a name and its associated channel from the pool.
     * @param name The name to remove
     */
    public static void removeName(String name){
        channelMap.remove(name);
    }

    /**
     * Finds the channel associated with this name.
     * @param name The name to search for
     * @return The requested channel, or null if it does not exist
     */
    public static Channel lookup(String name){
        return channelMap.get(name);
    }

    /**
     * Gets the name associated with this channel.
     * @param name The channel to lookup
     * @return The name of that channel, or an error string otherwise
     */
    public static String nameByChannel(Channel name){
        for(Map.Entry<String,Channel> ent : channelMap.entrySet()){
            if(ent.getValue().equals(name)){
                return ent.getKey();
            }
        }
        return "I'M_A_LITTLE_ERROR,_SHORT_AND_STOUT";
    }

    /**
     * Gets the list of names in this pool.
     * @return The list of player names
     */
    public static List<String> getNames(){
        return new ArrayList<>(channelMap.keySet());
    }
}
