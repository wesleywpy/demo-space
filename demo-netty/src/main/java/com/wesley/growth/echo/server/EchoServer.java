package com.wesley.growth.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * @author Created by Wesley on 2018/8/13.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            // 启动服务
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(group)
                    .channel(NioServerSocketChannel.class) //指定使用的Channel
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 对于所有的客户端连接来说，都会使用同一个 EchoServerHandler，因为其被标注为@Sharable
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            // 异步地绑定服务器；调用 sync()方法阻塞 等待直到绑定完成
            ChannelFuture future = boot.bind().sync();
            // 阻塞等待直到服务器的 Channel 关闭
            future.channel().closeFuture().sync();
        }finally {
            // 释放资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
        }

        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
}
