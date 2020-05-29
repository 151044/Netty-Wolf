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

public class RoleDispatch {
    private RoleDispatch(){
        throw new AssertionError();
    }
    private static Map<String,String> playerRoleMap = new HashMap<>();
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
            sb.append(Connections.nameByChannel(ch)).append(":").append(";");
        }
        Connections.openChannels().forEach(ch -> {
            ch.write(new Message("init_cache",sb.deleteCharAt(sb.length() - 1).toString()));
            ch.flush();
        });
        Connections.openChannels().forEach(ch -> {
            ch.write(new Message("chat","Night has fallen."));
            List<String> toUnwrap = RoleOrder.next();
            ch.write(new Message(toUnwrap.get(0),toUnwrap.get(1)));
            ch.flush();
        });
        Connections.getNames().forEach(GameState::setPlayer);
    }
    public static String roleFromName(String name){
        return playerRoleMap.get(name);
    }
}