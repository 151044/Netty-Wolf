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

package com.colin.games.werewolf.mod.killer.client;

import com.colin.games.werewolf.common.roles.Group;

public class KillerGroup implements Group {
    @Override
    public boolean isGood() {
        return false;
    }

    @Override
    public String getName() {
        return "Killer";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof KillerGroup;
    }
}
