package com.example.demo.note.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端请求handler
 * Created by zhouwei on 2017/12/27
 **/
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    private static  final Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("SimpleServerHandler.channelRead ----start");
        ByteBuf result = (ByteBuf) msg;
        byte[] bs = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(bs);
        String resultStr = new String(bs);
        // 接收并打印客户端的信息
        logger.info("Client msg: "+ resultStr);
        // 释放资源，这行很关键
        result.release();

        // 向客户端发送消息
        String response = "hello client!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
        ctx.write(resp);
        ctx.flush();
        logger.info("SimpleServerHandler.channelRead ----end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
