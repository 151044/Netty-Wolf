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

/**
 * Describes a group.<br>
 * When only members of a group remain alive, the group wins.
 */
public interface Group {
    /**
     * Tests if this group is benign.
     * @return True if this group is good, false otherwise
     */
    boolean isGood();

    /**
     * Gets the name of this group.
     * @return The name of this group
     */
    String getName();
}
