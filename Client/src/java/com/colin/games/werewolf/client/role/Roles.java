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

package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.common.roles.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A registry for roles. <br>
 * Mods should register their custom roles here.
 */
public class Roles {
    private Roles(){
        throw new AssertionError();
    }
    private static final Map<String, Supplier<? extends Role>> makeMap = new HashMap<>();

    /**
     * Registers a role. The role can then be created by using the name supplied and the supplier.
     * @param lookupName The name to be used as lookup
     * @param supplier The supplier to create new role instances.
     */
    public static void register(String lookupName,Supplier<? extends Role> supplier) {
        makeMap.put(lookupName,supplier);
    }

    /**
     * Creates a new role instance with the specified name.
     * @param lookup The String to lookup
     * @return The new role instance
     */
    public static Role makeNew(String lookup) {
        Objects.requireNonNull(lookup);
        return makeMap.get(lookup).get();
    }
}
