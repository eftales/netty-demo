package edu.uestc.sdn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ACProtocolEncoder extends MessageToByteEncoder<ACProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ACProtocol msg, ByteBuf out) throws Exception {
        System.out.println("ACProtocolEncoder encode 方法被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
