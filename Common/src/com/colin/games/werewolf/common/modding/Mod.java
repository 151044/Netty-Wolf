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

import com.colin.games.werewolf.common.modding.gui.DefaultModScreen;

import java.util.List;

/**
 * The mod interface. All modifications must implement this interface to be recognized as a mod and loaded.
 */
public interface Mod {
    boolean isServer();
    boolean isClient();
    List<ModType> modified();
    void init();
    void lateInit();
    void cleanup();
    String name();
    String author();
    String version();
    default String desc(){
        return "The default description. Shouldn't you change it?";
    }
    default void infoScreen(){
        new DefaultModScreen(this);
    }
}
