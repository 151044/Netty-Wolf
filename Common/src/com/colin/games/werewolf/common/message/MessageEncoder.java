package com.colin.games.werewolf.common.message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message s, List<Object> list) {
        list.add(s.toString() + "\n");
    }
}
