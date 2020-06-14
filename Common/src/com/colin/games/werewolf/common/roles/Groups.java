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

package com.colin.games.werewolf.common.roles;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for {@link com.colin.games.werewolf.common.roles.Group Group}s.
 */
public class Groups {
    private Groups(){
        throw new AssertionError();
    }
    private static final Map<String,String> roleToGroup = new HashMap<>();
    static{
        roleToGroup.put("Guard","Villager");
        roleToGroup.put("Seer","Villager");
        roleToGroup.put("Villager","Villager");
        roleToGroup.put("Witch","Villager");
        roleToGroup.put("Hunter","Villager");
        roleToGroup.put("Werewolf","Werewolf");
    }

    /**
     * Registers a role to belong to the specified group.
     * @param role The role which should be set to this group
     * @param group The group to set this role to
     */
    public static void register(String role,String group){
        roleToGroup.put(role,group);
    }

    /**
     * Gets the group that this role belongs to.
     * @param role The role to lookup its group
     * @return The group, in the form of a String
     */
    public static String getGroup(String role){
        return roleToGroup.get(role);
    }
}
