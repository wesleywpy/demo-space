package com.wesley.growth.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

/**
 * @author Created by Wesley on 2018/8/13.
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            // 启动服务
            Bootstrap boot = new Bootstrap();
            boot.group(group)
                .channel(NioSocketChannel.class) //指定使用的Channel
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 对于所有的客户端连接来说，都会使用同一个 EchoServerHandler，因为其被标注为@Sharable
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });

            // 连接到远程节点， 阻塞等待直到连接完成
            ChannelFuture future = boot.connect().sync();
            // 阻塞， 直到Channel 关闭
            future.channel().closeFuture().sync();

        }finally {
            // 关闭线程池并释放资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }
}
