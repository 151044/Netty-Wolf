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
import com.colin.games.werewolf.common.roles.Groups;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Holds information as to if the person has died or not.
 */
public class GameState {
    private GameState(){
        throw new AssertionError();
    }
    private static final Map<String,Boolean> isAlive = new HashMap<>();
    private static String killedByWolf = "I'M_A_LITTLE_ERROR,_SHORT_AND_STOUT";
    private static final Map<String,BitSet> cache = new HashMap<>();
    private static final List<String> killed = new ArrayList<>();
    public static void setPlayer(String name){
        isAlive.put(name,true);
        cache.put(name,new BitSet());
    }
    public static void protect(Message msg){
        cache.get(msg.getContent()).set(3);
    }
    public static void heal(Message msg){
        cache.get(killedByWolf).set(2);
    }
    public static void killAsWolf(Message msg){
        cache.get(msg.getContent()).set(0);
        killedByWolf = msg.getContent();
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
                killed.add(ent.getKey());
                sb.append(ent.getKey()).append(":kill;");
                continue;
            }
            if(ops.get(0) && !ops.get(2) && !ops.get(3)){
                sb.append(ent.getKey()).append(":kill;");
                isAlive.put(ent.getKey(),false);
                killed.add(ent.getKey());
            }
        }
        String str = sb.deleteCharAt(sb.length() - 1).toString();
        Connections.openChannels().forEach(ch -> ch.writeAndFlush(new Message("cache_update",str.isBlank() ? "empty" : str)));
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
        String str = sb.deleteCharAt(sb.length() - 1).toString();
        Connections.openChannels().forEach(ch -> ch.writeAndFlush(new Message("cache_update",str.isBlank() ? "empty" : str)));
        cache.forEach((string,bs) -> bs.clear());
    }
    public static GameCondition checkWinCon(){
        boolean werewolfWin = true,villagerWin = true;
        for(String s : isAlive.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList())){
            String group = Groups.getGroup(RoleDispatch.roleFromName(s));
            if(group.equals("Werewolf")){
                villagerWin = false;
            }else if(group.equals("Villager")){
                werewolfWin = false;
            }
        }
        if(werewolfWin){
            return DefaultConditions.WIN_WEREWOLF;
        }
        if(villagerWin){
            return DefaultConditions.WIN_VILLAGERS;
        }
        if(isAlive.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).count() == 0){
            return DefaultConditions.WIN_NONE;
        }
        return DefaultConditions.CONTINUE;
    }
    public static GameCondition checkWinCon(Function<Map<String,Boolean>,GameCondition> winFunction){
        return winFunction.apply(isAlive);
    }

    public static String getWolfKill() {
        return killedByWolf;
    }
    public static void directKill(String name){
        isAlive.put(name,false);
    }
    public static List<String> getKilled(){
        return killed;
    }
    public static void clearKilled(){
        killed.clear();
    }
}
