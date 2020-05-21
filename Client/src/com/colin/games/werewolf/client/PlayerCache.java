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
}
