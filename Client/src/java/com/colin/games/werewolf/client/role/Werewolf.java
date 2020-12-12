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
import com.colin.games.werewolf.client.role.gui.WerewolfPane;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Group;
import com.colin.games.werewolf.common.roles.Role;
import com.colin.games.werewolf.common.roles.WrapperPane;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.List;

/**
 * The Werewolf role.<br>
 * The Werewolves can kill one player per night.
 */
public class Werewolf implements Role {
    private WerewolfPane pane = null;
    /**
     * Constructs a new Werewolf instance.
     */
    public Werewolf(){

    }
    @Override
    public String name() {
        return "Werewolf";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        List<String> otherWolves = Arrays.asList(msg.getContent().split(","));
        if(!pane.hasInitialized()){
            pane.initLabels(otherWolves);
        }else{
            pane.updateFrame(otherWolves);
        }
        pane.setVisible(true);
    }

    @Override
    public String callbackName() {
        return "werewolf_next";
    }

    @Override
    public Group getGroup() {
        return DefaultGroups.WEREWOLF;
    }

    @Override
    public WrapperPane getActionPane() {
        WerewolfPane pane = new WerewolfPane();
        this.pane = pane;
        return new WrapperPane(pane);
    }

    @Override
    public String toString() {
        return name();
    }
}
