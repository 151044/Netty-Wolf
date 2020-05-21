package com.colin.games.werewolf.server;

import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.server.gui.InitFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ServerMain {
    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Environment.setSide(Environment.Side.SERVER);
        new InitFrame();
    }
}
