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

public class MessageDispatch {
    private MessageDispatch(){
        throw new AssertionError();
    }
    private static final boolean debug = true;
    private static final Map<String, BiConsumer<ChannelHandlerContext,Message>> lookup = new HashMap<>();
    public static void dispatch(ChannelHandlerContext ctx,Message m){
        lookup.getOrDefault(m.getType(),(c,msg) -> {/*No op */
        if(debug){
            System.out.println(msg);
        }
        }).accept(ctx,m);
    }
    public static void register(String str, BiConsumer<ChannelHandlerContext,Message> cons){
        lookup.put(str,cons);
    }

}
