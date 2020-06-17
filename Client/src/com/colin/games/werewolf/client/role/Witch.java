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
import com.colin.games.werewolf.client.role.gui.WitchFrame;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Group;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

/**
 * The Witch role.<br>
 * The Witch can heal and kill a player, but only once.
 */
public class Witch implements Role {
    private boolean isHealUsed = false;
    private boolean isKillUsed = false;

    /**
     * Constructs a new Witch instance.
     */
    public Witch(){

    }
    @Override
    public String name() {
        return "Witch";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        String died = msg.getContent();
        new WitchFrame(died,this);
    }

    @Override
    public String callbackName() {
        return "witch_next";
    }

    @Override
    public Group getGroup() {
        return DefaultGroups.VILLAGER;
    }

    /**
     * Gets if the heal is used.
     * @return True if the heal of the witch is used, false otherwise
     */
    public boolean isHealUsed(){
        return isHealUsed;
    }

    /**
     * Gets if the killing ability is used.
     * @return True if the killing ability of the witch is used, false otherwise
     */
    public boolean isKillUsed(){
        return isKillUsed;
    }

    /**
     * Sets the heal ability to the given state.
     * @param toSet The state of the ability to set
     */
    public void setKillStatus(boolean toSet){
        isKillUsed = toSet;
    }

    /**
     * Sets the kill ability to the given state.
     * @param toSet The state of the ability to set
     */
    public void setHealStatus(boolean toSet){
        isHealUsed = toSet;
    }
    @Override
    public String toString() {
        return name();
    }
}
