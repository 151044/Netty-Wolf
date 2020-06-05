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

package com.colin.games.werewolf.common.message;

/**
 * Thrown to indicate that a {@link com.colin.games.werewolf.common.message.Message Message} instance
 * received by this instance is malformed.
 */
public class MalformedMessageException extends RuntimeException{
    /**
     * Constructs a new MalformedMessageException with the specified erroring string.
     * @param s The string which is malformed
     */
    public MalformedMessageException(String s){
        super(s);
    }
}
