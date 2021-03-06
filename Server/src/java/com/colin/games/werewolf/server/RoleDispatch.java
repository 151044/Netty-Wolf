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

package com.colin.games.werewolf.server;
import com.colin.games.werewolf.common.message.Message;
import io.netty.channel.Channel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Allocates roles.
 */
public class RoleDispatch {
    private RoleDispatch(){
        throw new AssertionError();
    }
    private static final Map<String,String> playerRoleMap = new HashMap<>();

    /**
     * Sends random roles to every player, using the map supplied.
     * A role, in the form of a String, is randomly assigned to every player.
     * @param toDispatch The map of number of roles
     */
    public static void handle(Map<String,Integer> toDispatch){
        List<String> pool = toDispatch.entrySet().stream().map(ent -> {
            List<String> res = new ArrayList<>();
            for(int i = 0; i < ent.getValue(); i++){
                res.add(ent.getKey());
            }
            return res;
        }).flatMap(Collection::stream).collect(Collectors.toList());
        List<Channel> channels = Connections.openChannels();
        Collections.shuffle(pool);
        if(channels.size() != pool.size()){
            throw new IllegalStateException("Impossible: Number of players not equal to number of roles?");
        }
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(Channel ch : channels){
            String toSend = pool.get(rand.nextInt(pool.size()));
            playerRoleMap.put(Connections.nameByChannel(ch),toSend);
            pool.remove(toSend);
            sb.append(Connections.nameByChannel(ch)).append(":").append(playerRoleMap.get(Connections.nameByChannel(ch))).append(";");
        }
        String send = sb.deleteCharAt(sb.length() - 1).toString();
        Connections.openChannels().forEach(ch -> {
            ch.write(new Message("init_cache",send));
            ch.flush();
        });
        List<String> toUnwrap = RoleOrder.next();
        Connections.openChannels().forEach(ch -> {
            ch.write(new Message("chat","Night has fallen."));
            ch.flush();
            ch.write(new Message("night","empty"));
            ch.flush();
            ch.write(new Message(toUnwrap.get(0),toUnwrap.get(1)));
            ch.flush();
        });
        Connections.getNames().forEach(GameState::setPlayer);
    }

    /**
     * Gets the role of a person by name.
     * @param name The name of the player to try to find a role for
     * @return The role of the player
     */
    public static String roleFromName(String name){
        return playerRoleMap.get(name);
    }

    /**
     * Gets all the players of a given role.
     * @param name The name of the role to lookup
     * @return The list of players with that role
     */
    public static List<String> getAllByRole(String name){
        return playerRoleMap.entrySet().stream().filter(ent -> ent.getValue().equals(name)).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Check if a role exist.
     * @param name The name of the role to lookup
     * @return True if the role exists, false otherwise
     */
    public static boolean hasRole(String name){
        return playerRoleMap.containsValue(name);
    }
}