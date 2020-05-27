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

public enum DefaultConditions implements GameCondition{
    CONTINUE(){
        @Override
        public boolean hasWon() {
            return false;
        }

        @Override
        public String reason() {
            return "empty";
        }
    }
    ,WIN_WEREWOLF {
        @Override
        public String reason() {
            return "The werewolves have killed everyone!";
        }
    },WIN_VILLAGERS {
        @Override
        public String reason() {
            return "The villagers have unmasked all werewolves!";
        }
    };

    @Override
    public boolean hasWon() {
        return true;
    }
}
