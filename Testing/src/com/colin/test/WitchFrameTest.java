package com.colin.test;
import com.colin.games.werewolf.client.role.Witch;
import com.colin.games.werewolf.client.role.gui.WitchFrame;
import com.colin.games.werewolf.common.message.Message;

public class WitchFrameTest {
    public static void main(String[] args) {
        Init.init();
        Witch w = new Witch();
        w.action(null,new Message("witch_notify","Eric"));
    }
}
