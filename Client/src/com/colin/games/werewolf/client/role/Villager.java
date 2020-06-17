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

package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.client.role.groups.DefaultGroups;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Group;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

/**
 * The Villager role.<br>
 * It has no special abilities.
 */
public class Villager implements Role {
    /**
     * Constructs a new Villager instance.
     */
    public Villager(){

    }
    @Override
    public String name() {
        return "Villager";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        //No-op
    }

    @Override
    public String callbackName() {
        //no associated callback?
        return "villager_next";
    }

    @Override
    public Group getGroup() {
        return DefaultGroups.VILLAGER;
    }
    @Override
    public String toString() {
        return name();
    }
}
