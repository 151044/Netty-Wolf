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

import java.util.*;

/**
 * Maintains the state of voting.
 */
public class VotingState {
    private VotingState(){
        throw new AssertionError();
    }
    private static final Map<String,String> votes = new HashMap<>();
    public static void setVote(String name,String vote){
        votes.put(name,vote);
    }

    /**
     * Aggregates votes and sends a final result to all client.
     */
    public static void collect(){
        List<String> vote = new ArrayList<>(votes.values());
        Map<String,Integer> map = new HashMap<>();
        for(String s : vote){
            if(map.containsKey(s)){
                map.put(s,map.get(s) + 1);
            }else{
                map.put(s,1);
            }
        }
        Optional<Map.Entry<String,Integer>> opt = map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue));
        if(opt.isEmpty() || opt.get().getKey().equals("Abstain")){
            Connections.openChannels().forEach(ch -> {
                ch.write(new Message("chat","No one is killed!"));
                ch.flush();
            });
        }else{
            GameState.directKill(opt.get().getKey());
            Connections.openChannels().forEach(ch -> {
                ch.write(new Message("chat",opt.get().getKey() + " has been killed by the mob of angry villagers!"));
                ch.flush();
            });
        }
        GameCondition con = GameState.checkWinCon();
        if(con.hasWon()){
            Connections.openChannels().forEach(ch -> {
                ch.write(new Message("end",con.reason()));
                ch.flush();
            });
            System.exit(0);
        }
        Connections.openChannels().forEach(ch -> {
            ch.write(new Message("vote_term","empty"));
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
    }

    /**
     * Checks if the vote is completed.
     * @return True if the vote is done, false otherwise
     */
    public static boolean isDone(){
        return votes.size() == Connections.openChannels().size();
    }
}
