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

public class Connections {
    private Connections(){
        throw new AssertionError();
    }
    private static final Map<String,Channel> channelMap = new HashMap<>();
    public static void add(String s, Channel ch){
        channelMap.put(s,ch);
    }
    public static boolean has(String s){
        return channelMap.containsKey(s);
    }
    public static List<Channel> openChannels(){
        return new ArrayList<>(channelMap.values());
    }
    public static void removeName(String name){
        channelMap.remove(name);
    }
    public static Channel lookup(String name){
        return channelMap.get(name);
    }
}
