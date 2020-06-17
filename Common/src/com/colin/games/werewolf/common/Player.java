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

package com.colin.games.werewolf.common;

import com.colin.games.werewolf.common.roles.Role;

import java.util.Objects;

/**
 * Representation of a player.
 * Contains information such as name, death status and role.
 */
public class Player {
    private final String name;
    private boolean isDead = false;
    private final Role role;

    /**
     * Constructs a new player with the specified name and role.
     * @param name The name of the player to set
     * @param role The role of the player
     */
    public Player(String name,Role role){
        Objects.requireNonNull(name);
        Objects.requireNonNull(role);
        this.name = name;
        this.role = role;
    }

    /**
     * Gets the name of this player.
     * @return The name of this player
     */
    public String getName(){
        return name;
    }

    /**
     * Checks if this player is dead.
     * @return True if the player is deceased, false otherwise
     */
    public boolean isDead(){
        return isDead;
    }

    /**
     * Kills the player.
     */
    public void kill(){
        isDead = true;
    }

    /**
     * Gets the role of the player.
     * The role enables players to take actions related to their responsibilities.
     * @return The role of this player
     */
    public Role getRole(){
        return role;
    }

    @Override
    public String toString() {
        return name;
    }
}

