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

/**
 * Stores the order in which the roles perform their actions.
 * This class handles sending messages to trigger the next role's actions.
 * It internally stores a list of suppliers to get a role's message content.
 */
public class RoleOrder {
    private RoleOrder(){
        throw new AssertionError();
    }
    private static final Map<String,String> roleToCallback = new HashMap<>();
    private static final List<String> order = new ArrayList<>();
    private static final Map<String, Supplier<String>> associatedMsg = new HashMap<>();
    private static int current = 0;

    /**
     * Sets a role to perform its action before the given role.
     * @param before The role after the role to set
     * @param role The role to actually set
     * @param callback The callback to send as the type of a message
     */
    public static void setBefore(String before,String role,String callback){
        int pos = order.indexOf(before);
        if(pos == -1){
            order.add(0,role);
        }else{
            order.add(pos - 1,role);
        }
        roleToCallback.put(role,callback);
    }

    /**
     * Sets a role to perform its action after the given role.
     * @param after The role before the role to set
     * @param role The role to actually set
     * @param callback The callback to send as the type of a message
     */
    public static void setAfter(String after,String role,String callback){
        int pos = order.indexOf(after);
        if(pos == -1){
            order.add(role);
        }else{
            order.add(pos + 1,role);
        }
        roleToCallback.put(role,callback);
    }

    /**
     * Gets the next role.
     * Any associated messages will be included as the second element of the returned list.
     * If there is no associated message, the String "empty" will be set as the second element.
     * @return A list containing the callback to send and the associated message, in that order
     */
    public static List<String> next(){
        if(current == (order.size())){
            current = 0;
        }
        List<String> toReturn = new ArrayList<>();
        String res = order.get(current);
        toReturn.add(roleToCallback.get(res));
        current++;
        Supplier<String> content = associatedMsg.get(res);
        if(content == null){
            toReturn.add("empty");
        }else{
            toReturn.add(content.get());
        }
        return toReturn;
    }

    /**
     * Checks if the next call to next() will cause the night to be over.
     * @return True if the the next next() loops over to the start of the role order list, false otherwise
     */
    public static boolean isNextLoopOver(){
        return current == order.size();
    }

    /**
     * Sets the contents of the message sent as the content of a role callback.
     * @param role The role to set the content of the message for
     * @param supp The supplier to supply a string to send as the content
     */
    public static void setMessageContents(String role,Supplier<String> supp){
        associatedMsg.put(role,supp);
    }

    /**
     * Gets the name of the role from its callback.
     * @param call The callback of the role to lookup
     * @return The role, or an error string if it does not exist
     */
    public static String roleFromCallback(String call){
        for(Map.Entry<String,String> ent : roleToCallback.entrySet()){
            if(ent.getValue().equals(call)){
                return ent.getKey();
            }
        }
        return "I'M_A_LITTLE_ERROR,_SHORT_AND_STOUT";
    }
}
