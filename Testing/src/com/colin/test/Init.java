package com.colin.test;

import com.colin.games.werewolf.client.PlayerCache;
import com.colin.games.werewolf.client.role.Roles;
import com.colin.games.werewolf.client.role.Seer;
import com.colin.games.werewolf.client.role.Villager;
import com.colin.games.werewolf.client.role.Werewolf;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Init {
    private Init(){
        throw new AssertionError();
    }
    public static void init(){
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Roles.register("Villager", Villager::new);
        Roles.register("Seer", Seer::new);
        Roles.register("Werewolf", Werewolf::new);
        PlayerCache.init("151044:Villager;151044a:Seer;Five-Nine:Werewolf");
    }
}
