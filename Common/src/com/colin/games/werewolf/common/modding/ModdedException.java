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
 * Thrown to indicate that a mod has crashed while loading.
 */
public class ModdedException extends RuntimeException {
    public ModdedException(Mod mod, Exception ex){
        super("Mod " + mod.name() + " has crashed while loading.",ex);
    }
    public ModdedException(Mod mod,String msg){
        super("Mod " + mod.name() + " has crashed. Reason:" + msg);
    }
    public ModdedException(String msg){
        super(msg);
    }
}
