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

package com.colin.games.werewolf.client.protocol;

import com.colin.games.werewolf.client.gui.ExceptionFrame;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDispatch;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles incoming messages.
 */
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {
    private static final boolean debug = true;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message m = (Message) msg;
        if(debug) {
            System.out.println(m.getContent() + " " + m.getType());
        }
        MessageDispatch.dispatch(ctx,m);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if(cause instanceof Error){
            cause.printStackTrace();
            System.exit(2);
        }else{
            new ExceptionFrame((Exception) cause,Thread.currentThread());
        }
    }
}
