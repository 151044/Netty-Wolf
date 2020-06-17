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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds a list of roles and their abbreviations.
 */
public class RoleList {
    private RoleList(){
        throw new AssertionError();
    }
    private static final Map<String,String> roleAbbr = new HashMap<>();
    static{
        roleAbbr.put("We","Werewolf");
        roleAbbr.put("Wi","Witch");
        roleAbbr.put("H","Hunter");
        roleAbbr.put("G","Guard");
        roleAbbr.put("S","Seer");
        roleAbbr.put("V","Villager");
    }

    /**
     * Registers a role.
     * @param full The full name of the role
     * @param abbreviation The abbreviation of the role
     */
    public static void register(String full,String abbreviation){
        roleAbbr.put(abbreviation,full);
    }

    /**
     * Gets roles from their abbreviation.
     * @param abbreviate The short form to look up the long form opf
     * @return The full role name, from the abbreviation
     */
    public static String getFromAbbreviation(String abbreviate){
        return roleAbbr.get(abbreviate);
    }

    /**
     * Gets a list of roles.
     * @return The list of registered roles
     */
    public static Collection<String> getRoles(){
        return roleAbbr.values();
    }
}
