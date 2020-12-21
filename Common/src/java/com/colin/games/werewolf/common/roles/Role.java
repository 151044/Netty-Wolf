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

package com.colin.games.werewolf.common.roles;

import com.colin.games.werewolf.common.message.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * A role represents what actions a player can take.
 */
public interface Role {
    /**
     * Gets the name of the role.
     * @return The name of the role
     */
    String name();

    /**
     * Takes action when the callback specified in {@link #callbackName() callbackName()} is received by the client.<br>
     * <b>Warning:</b> You must send the next message to the server to advance the game to the next person.
     * @param ctx The channel from which this message is received
     * @param msg The message received, with the type being {@link #callbackName() callbackName()}.
     */
    void action(ChannelHandlerContext ctx, Message msg);

    /**
     * Gets the callback of the role.
     * @return THe callback of the role
     */
    String callbackName();

    /**
     * Gets the group which this role belongs to.
     * @return The group of this role
     */
    Group getGroup();

    /**
     * Gets the pane where player action is taken on a call to {@link #action(ChannelHandlerContext, Message) action(ChannelHandlerContext, Message)} is received.
     * @return The pane where action occurs
     */
    WrapperPane getActionPane();
}
