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

package com.colin.games.werewolf.server;

import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDecoder;
import com.colin.games.werewolf.common.message.MessageDispatch;
import com.colin.games.werewolf.common.message.MessageEncoder;
import com.colin.games.werewolf.server.gui.ServerStatusFrame;
import com.colin.games.werewolf.server.protocol.ServerMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * The actual server instance.
 */
public class Server {
    private final int port;
    private final int maxPlayers;
    private static Server instance;
    private final ServerStatusFrame status = new ServerStatusFrame();
    /**
     * Creates a new, but not started, server with the specified port.
     * @param port The port to host this server at
     * @param maxPlayers The maximum number of players
     */
    public Server(int port,int maxPlayers){
        this.port = port;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Actually runs the Server.
     */
    public void run() {
        new Thread(() -> {
            System.out.println("Starting at port " + port + "!");
            registerCallbacks();
            EventLoopGroup receive = new NioEventLoopGroup();
            EventLoopGroup worker = new NioEventLoopGroup();
            try {
                ServerBootstrap boot = new ServerBootstrap();
                boot.group(receive, worker)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                ChannelPipeline p = socketChannel.pipeline();
                                p.addLast(new LineBasedFrameDecoder(3000));
                                p.addLast(new StringDecoder(CharsetUtil.UTF_8));
                                p.addLast(new MessageDecoder());
                                p.addLast(new ServerMessageHandler());
                                p.addLast(new StringEncoder());
                                p.addLast(new MessageEncoder());
                            }

                        });
                try {
                    ChannelFuture future = boot.bind(port).sync();
                    if(!future.isSuccess()){
                        future.cause().printStackTrace();
                    }
                    future.channel().closeFuture().sync();
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            } finally {
                receive.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }, "server").start();
    }
    private static void registerCallbacks(){
        //The chat callback
        MessageDispatch.register("chat", (ctx, msg) ->
                Connections.openChannels().forEach(ch -> {
                    ch.write(msg);
                    ch.flush();
                }));
        //The query if name exists callback
        MessageDispatch.register("query_name", (ctx, msg) -> {
            String req = msg.getContent();
            ctx.channel().write(new Message("name_res", Connections.has(req) ? "false" : "true"));
            ctx.channel().flush();
            if (!Connections.has(req)) {
                Connections.add(req, ctx.channel());
                Server.getInstance().statusFrame().updatePlayers(req);
                Connections.openChannels().forEach(ch -> {
                    ch.write(new Message("chat", "[Server]: " + req + " has joined!"));
                    int left = Server.getInstance().maxPlayers - Connections.openChannels().size();
                    if(left < 1){
                        ch.write(new Message("chat","Ready to start at the host's command!"));
                        Server.getInstance().statusFrame().enableStart();
                    }else{
                        ch.write(new Message("chat","[Server]: Waiting for " + left + " more players."));
                    }
                    ch.flush();
                });
            }
        });
        //The disconnect callback
        MessageDispatch.register("disconnect", (ctx, msg) -> {
            ctx.channel().flush().close();
            Connections.removeName(msg.getContent());
            Server.getInstance().statusFrame().removePlayers(msg.getContent());
            Connections.openChannels().forEach(ch -> {
                ch.write(new Message("chat", "[Server]: " + msg.getContent() + " has disconnected!"));
                ch.flush();
            });
        });
        //The werewolf choice callback
        MessageDispatch.register("wolf_init",(ctx,msg) -> Connections.openChannels().forEach(ch -> {
            ch.write(msg);
            ch.flush();
        }));
        //The werewolf kill callback
        MessageDispatch.register("werewolf_kill",(ctx,msg) -> {
            GameState.killAsWolf(msg);
            Connections.openChannels().forEach(ch -> ch.write(new Message("werewolf_term","empty")));
        });
        //The witch kill callback
        MessageDispatch.register("witch_kill",(ctx,msg) -> GameState.killAsWitch(msg));
        //The witch heal callback
        MessageDispatch.register("witch_heal",(ctx,msg) -> GameState.heal(msg));
        //The guard callback
        MessageDispatch.register("guard",(ctx,msg) -> GameState.protect(msg));
        //The next callback
        MessageDispatch.register("next",(ctx,msg) -> {
            if(RoleOrder.isNextLoopOver()) {
                GameState.applyOutstanding();
                GameCondition con = GameState.checkWinCon();
                Connections.openChannels().forEach(ch -> {
                    ch.write(new Message("day", "empty"));
                    ch.flush();
                    ch.write(new Message("chat","Day has broken."));
                    ch.flush();
                    List<String> killed = GameState.getKilled();
                    ch.write(new Message("chat","[Server]: "  + ((killed.size() == 0) ? "No one" : String.join(" and ", killed)) + " has died!"));
                    if (con.hasWon()) {
                        ch.write(new Message("end", con.reason()));
                        ch.flush();
                    }
                    ch.flush();
                    ch.write(new Message("chat","Please discuss. You have 2 minutes."));
                    ch.flush();
                    });
                if(con.hasWon()){
                    System.exit(0);
                }
                GameState.clearKilled();
                new Thread(() -> {
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Connections.openChannels().forEach(chan -> {
                        chan.write(new Message("vote_start", "empty"));
                        chan.flush();
                    });
                }, "waiter").start();
            }else{
                List<String> next = RoleOrder.next();
                while(!RoleDispatch.hasRole(RoleOrder.roleFromCallback(next.get(0)))){
                    next = RoleOrder.next();
                }
                //Must be final or effectively final
                List<String> finalNext = next;
                Connections.openChannels().forEach(ch -> {
                    ch.write(new Message(finalNext.get(0), finalNext.get(1)));
                    ch.flush();
                });
            }
        });
        //The is full callback
        MessageDispatch.register("full_query",(ctx,msg) -> {
            ctx.channel().write(new Message("is_full_res",Server.getInstance().maxPlayers() < Connections.openChannels().size() ? "true" :"false"));
            ctx.channel().flush();
        });
        RoleOrder.setAfter("empty","Werewolf","werewolf_next");
        RoleOrder.setMessageContents("Werewolf",() -> String.join(",", RoleDispatch.getAllByRole("Werewolf")));
        RoleOrder.setAfter("Werewolf","Guard","guard_next");
        RoleOrder.setAfter("Guard","Witch","witch_next");
        RoleOrder.setAfter("Witch","Seer","seer_next");
        RoleOrder.setMessageContents("Witch", GameState::getWolfKill);
        MessageDispatch.register("vote_init",(ctx,msg) -> Connections.openChannels().forEach(ch -> {
            ch.write(msg);
            ch.flush();
        }));
        MessageDispatch.register("vote_final",(ctx,msg) -> {
            Connections.openChannels().forEach(ch -> {
                ch.write(msg);
                ch.flush();
            });
            String[] split = msg.getContent().split(",");
            VotingState.setVote(split[0],split[1]);
            if(VotingState.isDone()){
                VotingState.collect();
            }
        });
    }
    public static Server getInstance(){
        return instance;
    }
    public static void setInstance(Server s){
        instance = s;
    }
    public int maxPlayers(){
        return maxPlayers;
    }
    public ServerStatusFrame statusFrame(){
        return status;
    }
}
