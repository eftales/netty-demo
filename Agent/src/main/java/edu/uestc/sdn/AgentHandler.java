package edu.uestc.sdn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

import struct.*;


public class AgentHandler extends SimpleChannelInboundHandler<ACProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ACProtocol msg) throws Exception {
        //接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        Packet_in packet_out = new Packet_in();

        try {
            JavaStruct.unpack(packet_out, content);
            

        }catch(StructException e) {
                e.printStackTrace();
        }
        switch(packet_out.reason){
            case 0:
            System.out.println("heart beat");
            break;
            default:
            System.out.println("ingress :"+packet_out.ingress_port);
            System.out.println("reason :"+packet_out.reason);
        }
        


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
