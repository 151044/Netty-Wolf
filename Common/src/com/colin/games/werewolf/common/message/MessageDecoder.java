/*
 * Netty-Wolf
 * Copyright (C) 2020  Colin Chow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.colin.games.werewolf.common.message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Decodes the message received into a Message instance.
 */
public class MessageDecoder extends MessageToMessageDecoder<String> {
    /**
     * Constructs a new MessageDecoder.
     */
    public MessageDecoder(){

    }
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
