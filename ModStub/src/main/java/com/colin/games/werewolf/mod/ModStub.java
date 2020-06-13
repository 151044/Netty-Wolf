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

package com.colin.games.werewolf.mod;

import com.colin.games.werewolf.common.modding.Mod;
import com.colin.games.werewolf.common.modding.ModType;

import java.util.List;

public class ModStub implements Mod {
    @Override
    public boolean isServer() {
        return true;
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public List<ModType> modified() {
        return List.of();
    }

    @Override
    public void init() {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public String name() {
        return "Test Mod";
    }
}