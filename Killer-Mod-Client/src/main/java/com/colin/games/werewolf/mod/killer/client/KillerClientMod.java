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

package com.colin.games.werewolf.mod.killer.client;

import com.colin.games.werewolf.client.role.Roles;
import com.colin.games.werewolf.common.message.MessageDispatch;
import com.colin.games.werewolf.common.modding.AbstractMod;
import com.colin.games.werewolf.common.modding.ModType;
import com.colin.games.werewolf.common.roles.Groups;

import java.util.List;

public class KillerClientMod extends AbstractMod {
    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public String name() {
        return "Killer-Mod";
    }

    @Override
    public String author() {
        return "151044";
    }

    @Override
    public String version() {
        return "v0.0.1";
    }

    @Override
    public List<ModType> modified() {
        return List.of(ModType.GROUP,ModType.ROLE,ModType.PRESET,ModType.WIN_CONDITION);
    }

    @Override
    public void init() {
        Roles.register("Killer",Killer::new);
        Groups.register("Killer","Killer");
        MessageDispatch.register("killer_role",(ctx,msg) -> {

        });
    }


    @Override
    public String desc() {
        return "Murderous intent, coming to a client near you.";
    }
}
