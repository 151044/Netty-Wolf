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

public class RoleOrder {
    private RoleOrder(){
        throw new AssertionError();
    }
    private static Map<String,String> roleToCallback = new HashMap<>();
    private static List<String> order = new ArrayList<>();
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
            order.add(pos,role);
        }
        roleToCallback.put(role,callback);
    }
    public static String next(){
        String res = order.get(current);
        if(current ==  order.size() - 1){
            current = 0;
        }else{
            current++;
        }
        return res;
    }
}
