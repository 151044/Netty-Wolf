package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.client.role.gui.WerewolfFrame;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.List;

public class Werewolf implements Role {
    @Override
    public String name() {
        return "Werewolf";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        List<String> otherWolves = Arrays.asList(msg.getContent().split(","));
        new WerewolfFrame(otherWolves);
    }

    @Override
    public boolean isGood() {
        return false;
    }
}
