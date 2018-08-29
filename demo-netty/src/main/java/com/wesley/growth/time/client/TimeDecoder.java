package com.wesley.growth.time.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Wesley
 * Created by 2018/08/27
 */
public class TimeDecoder extends ByteToMessageDecoder {

    /**
     * 每当有新数据接收的时候，ByteToMessageDecoder 都会调用 decode() 方法来处理内部的那个累积缓冲
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){
            return;
        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
