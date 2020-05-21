package com.colin.games.werewolf.common.message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MessageDecoder extends MessageToMessageDecoder<String> {
    @Override
    @SuppressWarnings("unchecked")
    protected void decode(ChannelHandlerContext channelHandlerContext, String str, List list) {
        String[] arr = str.split("://:");
        if(arr.length != 2){
            throw new MalformedMessageException("Malformed message received! Message: " + str);
        }
        list.add(new Message(arr[0],arr[1]));
    }
}
