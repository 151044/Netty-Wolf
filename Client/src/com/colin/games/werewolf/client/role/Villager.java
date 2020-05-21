package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

public class Villager implements Role {
    @Override
    public String name() {
        return "Villager";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        //No-op
    }

    @Override
    public boolean isGood() {
        return true;
    }
}
