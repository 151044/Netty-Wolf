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

package com.colin.games.werewolf.common.modding;

/**
 * The modules that a Mod modifies.
 */
public enum ModType {
    /**
     *  The Role module.
     */
    ROLE(){
        @Override
        public String getName() {
            return "Role";
        }
    },
    /**
     *  The Group module.
     */
    GROUP(){
        @Override
        public String getName() {
            return "Group";
        }
    },
    /**
     *  The Preset module.
     */
    PRESET(){
        @Override
        public String getName() {
            return "Preset";
        }
    },
    /**
     *  The Win Condition module.
     */
    WIN_CONDITION(){
        @Override
        public String getName() {
            return "Win condition";
        }
    },
    /**
     * The Other module.
     */
    OTHER(){
        @Override
        public String getName() {
            return "Other Mod Type";
        }
    };

    /**
     * Gets the name of the module which has been changed.
     * @return A user-readable string describing what has been changed.
     */
    public abstract String getName();
}
