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
import com.colin.games.werewolf.client.role.gui.GuardPane;
import com.colin.games.werewolf.common.Player;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Group;
import com.colin.games.werewolf.common.roles.Role;
import com.colin.games.werewolf.common.roles.WrapperPane;
import io.netty.channel.ChannelHandlerContext;

/**
 * The Guard role.<br>
 * The Guard role can protect any player from being killed, but cannot protect the same person twice in a row.
 */
public class Guard implements Role {
    private Player lastProtected = null;
    private GuardPane pane = null;

    /**
     * Constructs a new Guard instance.
     */
    public Guard(){

    }
    @Override
    public String name() {
        return "Guard";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        pane.setVisible(true);
        pane.updateGUI(this);
    }

    @Override
    public String callbackName() {
        return "guard_next";
    }

    @Override
    public Group getGroup() {
        return DefaultGroups.VILLAGER;
    }

    @Override
    public WrapperPane getActionPane() {
        pane = new GuardPane(this);
        return new WrapperPane(pane);
    }

    /**
     * Gets the last protected player.
     * @return The last protected player, and null if it does not exist
     */
    public Player lastSaved(){
        return lastProtected;
    }

    /**
     * Sets the last protected player
     * @param player The player to set
     */
    public void setSaved(Player player){
        lastProtected = player;
    }

    @Override
    public String toString() {
        return name();
    }
}
