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
import com.colin.games.werewolf.client.role.gui.SeerPane;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Group;
import com.colin.games.werewolf.common.roles.Role;
import com.colin.games.werewolf.common.roles.WrapperPane;
import io.netty.channel.ChannelHandlerContext;

/**
 * The Seer role.<br>
 * The Seer can lookup whether a person is good or bad once per night.
 */
public class Seer implements Role {
    private SeerPane pane = new SeerPane();
    /**
     * Constructs a new Seer instance.
     */
    public Seer(){

    }
    @Override
    public String name() {
        return "Seer";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        pane.setVisible(true);
    }

    @Override
    public String callbackName() {
        return "seer_next";
    }

    @Override
    public Group getGroup() {
        return DefaultGroups.VILLAGER;
    }

    @Override
    public WrapperPane getActionPane() {
        return new WrapperPane(pane);
    }

    @Override
    public String toString() {
        return name();
    }
}
