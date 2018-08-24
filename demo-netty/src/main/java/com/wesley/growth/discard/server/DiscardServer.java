package com.wesley.growth.discard.server;

import com.wesley.growth.echo.server.EchoServerHandler;
import com.wesley.growth.time.server.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2018/08/24
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        final TimeServerHandler serverHandler = new TimeServerHandler();
        try{
            // 启动服务
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 对于所有的客户端连接来说，都会使用同一个 EchoServerHandler，因为其被标注为@Sharable
                            ch.pipeline().addLast(serverHandler);
                        }
                    })
                    // Channel的配置参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 异步地绑定服务器；调用 sync()方法阻塞 等待直到绑定完成
            ChannelFuture future = boot.bind().sync();
            // 阻塞等待直到服务器的 Channel 关闭
            future.channel().closeFuture().sync();
        }finally {
            // 释放资源
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).start();
    }
}
