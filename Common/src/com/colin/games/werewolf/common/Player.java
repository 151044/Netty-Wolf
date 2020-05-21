package com.colin.games.werewolf.common;

import com.colin.games.werewolf.common.roles.Role;

import java.util.Objects;

public class Player {
    private final String name;
    private boolean isDead = false;
    private final Role role;
    public Player(String name,Role role){
        Objects.requireNonNull(name);
        Objects.requireNonNull(role);
        this.name = name;
        this.role = role;
    }
    public String getName(){
        return name;
    }
    public boolean isDead(){
        return isDead;
    }
    public void kill(){
        isDead = true;
    }
    public Role getRole(){
        return role;
    }

    @Override
    public String toString() {
        return name;
    }
}
