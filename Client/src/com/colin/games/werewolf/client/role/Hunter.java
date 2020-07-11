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
import com.colin.games.werewolf.client.role.gui.HunterFrame;
import com.colin.games.werewolf.client.role.gui.HunterPane;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Group;
import com.colin.games.werewolf.common.roles.Role;
import com.colin.games.werewolf.common.roles.WrapperPane;
import io.netty.channel.ChannelHandlerContext;

/**
 * The Hunter role.<br>
 * A Hunter can take revenge by killing another player when they are killed.
 */
public class Hunter implements Role {
    /**
     * Constructs a new Hunter instance.
     */
    public Hunter(){

    }
    @Override
    public String name() {
        return "Hunter";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        new HunterFrame();
    }

    @Override
    public String callbackName() {
        return "hunter_next";
    }

    @Override
    public Group getGroup() {
        return DefaultGroups.VILLAGER;
    }

    @Override
    public WrapperPane getActionPane() {
        return new WrapperPane(new HunterPane());
    }

    @Override
    public String toString() {
        return name();
    }
}
