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
}
