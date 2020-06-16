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

package com.colin.games.werewolf.client;

import com.colin.games.werewolf.client.gui.VotingFrame;
import com.colin.games.werewolf.client.protocol.ClientMessageHandler;
import com.colin.games.werewolf.client.role.*;
import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDecoder;
import com.colin.games.werewolf.common.message.MessageDispatch;
import com.colin.games.werewolf.common.message.MessageEncoder;
import com.colin.games.werewolf.common.modding.Mod;
import com.colin.games.werewolf.common.modding.ModLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.net.InetAddress;

/**
 * The main client class.
 */
public class Client {
    private final InetAddress addr;
    private final int port;
    private Channel chan;
    private static Client current;
    private String name;
    private ChannelFuture connect;
    private final Logger log = ClientMain.appendLog(LogManager.getFormatterLogger("Client"));

    /**
     * Constructs a new Client.
     * @param addr The IP to connect to
     * @param port The port to connect to
     */
    public Client(InetAddress addr,int port){
        this.addr = addr;
        this.port = port;
    }

    /**
     * Connects to the server on a separate thread.
     */
    public void run() {
        Thread t = new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap boot = new Bootstrap();
            boot.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new LineBasedFrameDecoder(3000));
                            p.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            p.addLast(new MessageDecoder());
                            p.addLast(new ClientMessageHandler());
                            p.addLast(new StringEncoder());
                            p.addLast(new MessageEncoder());
                        }
                    });
            try {
                connect = boot.connect(addr, port);
                log.info("Connecting to " + addr + " at port " + port + " ...");
                connect.sync();
                log.info("Connected!");
                chan = connect.channel();
                initCallbacks();
                log.info("Initialized callbacks!");
                if(Environment.isModded()){
                    lateInitMods();
                    log.info("Successfully completed init phase 2 of mods.");
                }
                connect.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"client");
        t.start();
    }

    /**
     * Gets the communication channel with the server.
     * @return The channel requested
     */
    public Channel getChannel(){
        return chan;
    }

    /**
     * Sets the name of the client.
     * @param name The name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the name of the client.
     * @return The name of this client
     */
    public String getName(){
        return name;
    }

    /**
     * Writes and flushes an object to the channel.
     * @param target The object to write
     */
    public void writeAndFlush(Object target){
        chan.write(target);
        chan.flush();
    }

    /**
     * Sets the current client.
     * @param cli The client to set
     */
    public static void setCurrent(Client cli){
        current = cli;
    }

    /**
     * Gets the current running client.
     * @return The client instance
     */
    public static Client getCurrent(){
        return current;
    }
    private static void initCallbacks(){
        MessageDispatch.register("kick",(ctx,msg) -> {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,"You have been kicked by the host.","Kick",JOptionPane.WARNING_MESSAGE));
            ctx.channel().write(new Message("disconnect",Client.getCurrent().getName()));
            ctx.channel().flush();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        MessageDispatch.register("init_cache",(ctx,msg) -> PlayerCache.init(msg.getContent()));
        MessageDispatch.register("cache_update",(ctx,msg) -> PlayerCache.update(msg.getContent()));
        MessageDispatch.register("vote_start",(ctx,msg) -> new VotingFrame());
        Roles.register("Werewolf", Werewolf::new);
        Roles.register("Guard", Guard::new);
        Roles.register("Hunter", Hunter::new);
        Roles.register("Seer", Seer::new);
        Roles.register("Villager",Villager::new);
        Roles.register("Witch", Witch::new);
        MessageDispatch.register("end",(ctx,msg) -> {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,"Game ended! Reason: " + msg.getContent(),"Game Ended",JOptionPane.INFORMATION_MESSAGE));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            if(Client.getCurrent() != null) {
                Client.getCurrent().getChannel().write(new Message("disconnect", Client.getCurrent().getName()));
                Client.getCurrent().getChannel().flush();
            }
        }));
    }
    private static void lateInitMods(){
        ModLoader.getLoaded().forEach(Mod::lateInit);
    }
    public ChannelFuture connectFuture(){
        return connect;
    }
}
