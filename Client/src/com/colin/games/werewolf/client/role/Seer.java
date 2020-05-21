package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.client.role.gui.SeerFrame;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

public class Seer implements Role {
    @Override
    public String name() {
        return "Seer";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        new SeerFrame();
    }

    @Override
    public boolean isGood() {
        return true;
    }
}
