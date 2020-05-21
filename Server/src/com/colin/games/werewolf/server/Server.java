package com.colin.games.werewolf.server;

import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.message.MessageDecoder;
import com.colin.games.werewolf.common.message.MessageDispatch;
import com.colin.games.werewolf.common.message.MessageEncoder;
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

/**
 * The actual server instance.
 */
public class Server {
    private final int port;

    /**
     * Creates a new, but not started, server with the specified port.
     * @param port The port to host this server at
     */
    public Server(int port){
        this.port = port;
    }

    /**
     * Actually runs the Server.
     */
    public void run() {
        new Thread(() -> {
        System.out.println("Starting at port " + port + "!");
        MessageDispatch.register("chat",(ctx,msg) ->
            Connections.openChannels().forEach(ch -> {
                ch.write(msg);
                ch.flush();
            }));
        MessageDispatch.register("query_name",(ctx,msg) -> {
            String req = msg.getContent();
            ctx.channel().write(new Message("name_res", Connections.has(req) ? "false":"true"));
            ctx.channel().flush();
            if(!Connections.has(req)){
                Connections.add(req,ctx.channel());
                Connections.openChannels().forEach(ch -> {
                    ch.write(new Message("chat",req + " has joined!"));
                    ch.flush();
                });
            }
        });
        MessageDispatch.register("disconnect",(ctx,msg) -> {
            ctx.channel().flush().close();
            Connections.openChannels().forEach(ch -> {
                ch.write(new Message("chat","[Server]: " + msg.getContent() + " has disconnected!"));
                ch.flush();
                Connections.removeName(msg.getContent());
            });
        });
        EventLoopGroup receive = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(receive,worker)
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
                future.channel().closeFuture().sync();
            }catch(InterruptedException ie){
                throw new RuntimeException(ie);
            }
        }finally{
            receive.shutdownGracefully();
            worker.shutdownGracefully();
        }
    },"server").start();
    }
}
