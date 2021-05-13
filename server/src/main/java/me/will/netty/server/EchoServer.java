package me.will.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class EchoServer {

    public static void main(String[] args) throws InterruptedException {
        new EchoServer().start();
        System.out.println(11111);
    }

    public void start() throws InterruptedException {
        final ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .localAddress(8001)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new EchoServerHandler());
                    }
                });

        ChannelFuture future = bootstrap.bind().sync(); //异步绑定，使用sync转为同步绑定，绑定成功前会堵塞
        future.channel().closeFuture().sync();
    }
}
