package com.colin.games.werewolf.client.role.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WerewolfFrame extends JFrame {
    private final Map<String,JLabel> map = new HashMap<>();
    public WerewolfFrame(List<String> others){
        super("Your turn!");

    }
}
