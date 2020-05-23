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

import com.colin.games.werewolf.common.message.Message;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Holds information as to if the person has died or not.
 */
public class GameState {
    private GameState(){
        throw new AssertionError();
    }
    private static final Map<String,Boolean> isAlive = new HashMap<>();
    private static final Map<String,BitSet> cache = new HashMap<>();
    public static void protect(Message msg){
        cache.get(msg.getContent()).set(3);
    }
    public static void heal(Message msg){
        cache.get(msg.getContent()).set(2);
    }
    public static void killAsWolf(Message msg){
        cache.get(msg.getContent()).set(0);
    }
    public static void killAsWitch(Message msg){
        cache.get(msg.getContent()).set(1);
    }
    public static void setArbitrarily(Message msg, int index){
        cache.get(msg.getContent()).set(index);
    }
    public static void applyOutstanding(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,BitSet> ent : cache.entrySet()){
            BitSet ops = ent.getValue();
            if(ops.get(1) && !ops.get(2)/*For the particularly insane people*/){
                isAlive.put(ent.getKey(),false);
                sb.append(ent.getKey()).append(":kill;");
                continue;
            }
            if(ops.get(0) && !ops.get(2) && !ops.get(3)){
                sb.append(ent.getKey()).append(":kill;");
                isAlive.put(ent.getKey(),false);
            }
        }
        Connections.openChannels().forEach(ch -> ch.writeAndFlush(new Message("cache_update",sb.toString())));
        checkWinCon();
        cache.clear();
    }
    public static void applyOutstanding(BiFunction<String,BitSet,Boolean> func){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,BitSet> ent : cache.entrySet()){
            boolean killOrNot = func.apply(ent.getKey(),ent.getValue());
            if(killOrNot){
                sb.append(ent.getKey()).append(":kill;");
            }
            isAlive.put(ent.getKey(),killOrNot);
        }
        Connections.openChannels().forEach(ch -> ch.writeAndFlush(new Message("cache_update",sb.toString())));
        checkWinCon();
        cache.clear();
    }
    private static void checkWinCon(){

    }
}
