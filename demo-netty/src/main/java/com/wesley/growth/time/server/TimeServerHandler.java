package com.wesley.growth.time.server;

import com.wesley.growth.time.client.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>
 *
 * </p>
 * @author Created by Wesley on 2018/08/24
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 分配4个字节的ByteBuf
//        final ByteBuf time = ctx.alloc().buffer(4);

        // (System.currentTimeMillis()/1000L 获取到的是格林威治时间，+2208988800 变成东八区的时间，否则会有时差
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // 一个 ChannelFuture 代表了一个还没有发生的 I/O 操作。这意味着任何一个请求操作都不会马上被执行。
//        final ChannelFuture f = ctx.writeAndFlush(time);
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        });
        ChannelFuture channelFuture = ctx.writeAndFlush(new UnixTime());
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
