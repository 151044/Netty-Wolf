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

package com.colin.games.werewolf.client.role.groups;

import com.colin.games.werewolf.client.role.Villager;
import com.colin.games.werewolf.client.role.Werewolf;
import com.colin.games.werewolf.common.roles.Group;

/**
 * The default groups.
 * @see Group Group
 */
public enum DefaultGroups implements Group {
    /**
     * The Villager group.
     * @see Villager Villager
     */
    VILLAGER(){
        @Override
        public boolean isGood() {
            return true;
        }

        @Override
        public String getName() {
            return "Villager";
        }
    },
    /**
     * The Werewolf group.
     * @see Werewolf Werewolf
     */
    WEREWOLF(){
        @Override
        public boolean isGood() {
            return false;
        }

        @Override
        public String getName() {
            return "Werewolf";
        }
    }
}
