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

/**
 * The audio subsystem.
 * The system is based on JNA, which interfaces with native C code, which is
 * implemented on top of SDL.
 * Currently, this system does not support Mac as no natives are compiled for it yet.
 * @see <a href="https://wiki.libsdl.org/"></a>
 * @see <a href="https://github.com/java-native-access/jna/"></a>
 */
package com.colin.games.werewolf.client.audio;