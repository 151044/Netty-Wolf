package com.colin.games.werewolf.common.roles;

import com.colin.games.werewolf.common.message.Message;
import io.netty.channel.ChannelHandlerContext;

public interface Role {
    String name();
    void action(ChannelHandlerContext ctx, Message msg);
    boolean isGood();
}
