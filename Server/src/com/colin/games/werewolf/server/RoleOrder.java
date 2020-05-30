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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RoleOrder {
    private RoleOrder(){
        throw new AssertionError();
    }
    private static final Map<String,String> roleToCallback = new HashMap<>();
    private static final List<String> order = new ArrayList<>();
    private static final Map<String, Supplier<String>> associatedMsg = new HashMap<>();
    private static int current = 0;
    public static void setBefore(String before,String role,String callback){
        int pos = order.indexOf(before);
        if(pos == -1){
            order.add(0,role);
        }else{
            order.add(pos - 1,role);
        }
        roleToCallback.put(role,callback);
    }
    public static void setAfter(String after,String role,String callback){
        int pos = order.indexOf(after);
        if(pos == -1){
            order.add(role);
        }else{
            order.add(pos + 1,role);
        }
        roleToCallback.put(role,callback);
    }
    public static List<String> next(){
        List<String> toReturn = new ArrayList<>();
        String res = order.get(current);
        toReturn.add(roleToCallback.get(res));
        if(current == (order.size() - 1)){
            current = 0;
        }else{
            current++;
        }
        Supplier<String> content = associatedMsg.get(res);
        if(content == null){
            toReturn.add("empty");
        }else{
            toReturn.add(content.get());
        }
        return toReturn;
    }
    public static boolean isNextLoopOver(){
        return current == order.size();
    }
    public static void setMessageContents(String role,Supplier<String> supp){
        associatedMsg.put(role,supp);
    }
    public static String roleFromCallback(String call){
        for(Map.Entry<String,String> ent : roleToCallback.entrySet()){
            if(ent.getValue().equals(call)){
                return ent.getKey();
            }
        }
        return "I'M_A_LITTLE_ERROR,_SHORT_AND_STOUT";
    }
}
