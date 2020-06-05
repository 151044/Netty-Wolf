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
 * The class which is transferred over the network. <br>
 * It consists of 2 components: Type and content.<br>
 * The type determines how the message is handled.<br>
 * The content is the actual content of the message.<br>
 * @see com.colin.games.werewolf.common.message.MessageDispatch MessageDispatch
 */
public class Message {
    private final String type;
    private final String content;

    /**
     * Constructs a new message which is composed of the given type and content.
     * @param type The type of the message to construct
     * @param content The content of the message to construct
     */
    public Message(String type,String content){
        this.type = type;
        this.content = content;
    }

    /**
     * Gets the type of this message.
     * @return The type of the message
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the content of this message.
     * @return The content of this message
     */
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return type + "://:" + content;
    }
}
