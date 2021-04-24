package edu.uestc.sdn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

import struct.*;

//处理业务的handler
public class ControllerHandler extends SimpleChannelInboundHandler<ACProtocol>{
    public int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ACProtocol msg) throws Exception {

        //接收到数据，并处理
        int len = msg.getLen();
        byte[] contentIn = msg.getContent();

        Packet_in packet_in = new Packet_in();

        try {
            JavaStruct.unpack(packet_in, contentIn);
            

        }catch(StructException e) {
                e.printStackTrace();
        }
        
        System.out.print("ingress :"+packet_in.ingress_port);
        System.out.print("reason :"+packet_in.reason);


        //回复消息
        Packet_in packet_out = new Packet_in();
        packet_out.ingress_port = 11;
        packet_out.reason = 21;


        byte[] contentOut = JavaStruct.pack(packet_out);

        ACProtocol msgOut = new ACProtocol();
        msgOut.setLen(contentOut.length);
        msgOut.setContent(contentOut);

        ctx.writeAndFlush(msgOut);


    }
}
