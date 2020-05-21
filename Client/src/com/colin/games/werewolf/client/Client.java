package com.colin.games.werewolf.client;

import com.colin.games.werewolf.client.protocol.ClientMessageHandler;
import com.colin.games.werewolf.common.message.MessageDecoder;
import com.colin.games.werewolf.common.message.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;

public class Client {
    private final InetAddress addr;
    private final int port;
    private Channel chan;
    private static Client current;
    private String name;

    public Client(InetAddress addr,int port){
        this.addr = addr;
        this.port = port;
    }
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
                ChannelFuture future = boot.connect(addr, port).sync();
                chan = future.channel();
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"client");
        t.start();
    }
    public Channel getChannel(){
        return chan;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void writeAndFlush(Object target){
        chan.writeAndFlush(target);
    }
    public static void setCurrent(Client cli){
        current = cli;
    }
    public static Client getCurrent(){
        return current;
    }
}
