package com.colin.games.werewolf.common.message;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MessageDispatch {
    private MessageDispatch(){
        throw new AssertionError();
    }
    private static final Map<String, BiConsumer<ChannelHandlerContext,Message>> lookup = new HashMap<>();
    public static void dispatch(ChannelHandlerContext ctx,Message m){
        lookup.get(m.getType()).accept(ctx,m);
    }
    public static void register(String str, BiConsumer<ChannelHandlerContext,Message> cons){
        lookup.put(str,cons);
    }

}
