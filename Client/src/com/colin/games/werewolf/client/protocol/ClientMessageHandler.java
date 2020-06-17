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

import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDispatch;
import com.colin.games.werewolf.common.utils.ExceptionFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles incoming messages.
 */
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {
    private static final Logger allMsg = LogManager.getFormatterLogger("MessageHandler");

    /**
     * Constructs a new ClientMessageHandler.
     */
    public ClientMessageHandler(){
        super();
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message m = (Message) msg;
        allMsg.debug("Received message " + m.getType() + " , content " + m.getContent());
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
