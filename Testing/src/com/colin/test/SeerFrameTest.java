package com.colin.test;

import com.colin.games.werewolf.client.role.Seer;

public class SeerFrameTest {
    public static void main(String[] args) {
        Init.init();
        new Seer().action(null,null);
    }
}
