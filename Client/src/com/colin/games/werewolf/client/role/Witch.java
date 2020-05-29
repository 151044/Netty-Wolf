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

import com.colin.games.werewolf.client.role.gui.WitchFrame;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

public class Witch implements Role {
    private boolean isHealUsed = false;
    private boolean isKillUsed = false;
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
    public boolean isGood() {
        return true;
    }

    @Override
    public String callbackName() {
        return "witch_next";
    }

    public boolean isHealUsed(){
        return isHealUsed;
    }
    public boolean isKillUsed(){
        return isKillUsed;
    }
    public void setKillStatus(boolean toSet){
        isKillUsed = toSet;
    }
    public void setHealStatus(boolean toSet){
        isHealUsed = toSet;
    }
}
