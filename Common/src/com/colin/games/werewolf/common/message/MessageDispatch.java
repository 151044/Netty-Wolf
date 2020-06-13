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

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Sends messages to their destination. <br>
 * Mods should register their own callbacks here.
 */
public class MessageDispatch {
    private MessageDispatch(){
        throw new AssertionError();
    }
    private static final boolean debug = true;
    private static final Map<String, BiConsumer<ChannelHandlerContext,Message>> lookup = new HashMap<>();

    /**
     * Dispatches a message (calls its callback) with the specified ChannelHandlerContext and Message.
     * @param ctx The context to use while invoking the callback
     * @param m The message to handle
     */
    public static void dispatch(ChannelHandlerContext ctx,Message m){
        lookup.getOrDefault(m.getType(),(c,msg) -> {/*No op */
        }).accept(ctx,m);
    }

    /**
     * Registers a callback to be invoked.
     * @param str The name of the callback
     * @param cons The consumer to receive the {@link com.colin.games.werewolf.common.message.Message Message}.
     */
    public static void register(String str, BiConsumer<ChannelHandlerContext,Message> cons){
        lookup.put(str,cons);
    }

}
