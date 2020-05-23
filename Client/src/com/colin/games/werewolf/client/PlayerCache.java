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

package com.colin.games.werewolf.client;

import com.colin.games.werewolf.client.role.Roles;
import com.colin.games.werewolf.common.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerCache {
    private PlayerCache(){
        throw new AssertionError();
    }
    private static List<Player> players = new ArrayList<>();
    public static void init(String fullMsg){
        players = Arrays.stream(fullMsg.split(";")).map(str -> str.split(":")).map(sArr -> new Player(sArr[0], Roles.makeNew(sArr[1]))).collect(Collectors.toList());
    }
    public static List<Player> notDead(){
        return players.stream().filter(p -> !p.isDead()).collect(Collectors.toList());
    }
    public static void update(String fullMsg){

    }
}
