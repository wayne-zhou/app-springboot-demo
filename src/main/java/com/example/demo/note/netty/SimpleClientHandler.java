package com.example.demo.note.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端handler
 * Created by zhouwei on 2017/12/27
 **/
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    private static  final Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("SimpleClientHandler.channelRead-----start");
        ByteBuf result = (ByteBuf) msg;
        byte[] bs = new byte[result.readableBytes()];
        result.readBytes(bs);
        logger.info("Server msg: "+ new String(bs));
        result.release();
        logger.info("SimpleClientHandler.channelRead-----end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }


    // 连接成功后，向server发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] request = "hello Server!".getBytes();
        ByteBuf req = Unpooled.buffer(request.length);
        ctx.writeAndFlush(req.writeBytes(request));
    }
}
