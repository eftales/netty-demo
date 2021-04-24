package edu.uestc.sdn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ACProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //需要将得到二进制字节码-> ACProtocol 数据包(对象)
        int length = in.readInt();

        byte[] content = new byte[length];
        in.readBytes(content);

        //封装成 ACProtocol 对象，放入 out， 传递下一个handler业务处理
        ACProtocol ACProtocol = new ACProtocol();
        ACProtocol.setLen(length);
        ACProtocol.setContent(content);

        out.add(ACProtocol);

    }
}
