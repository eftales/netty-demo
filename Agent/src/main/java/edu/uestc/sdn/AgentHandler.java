package edu.uestc.sdn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

import struct.*;


public class AgentHandler extends SimpleChannelInboundHandler<ACProtocol> {

    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据 "今天天气冷，吃火锅" 编号

        Packet_in packet_in = new Packet_in();
        packet_in.ingress_port = 1;
        packet_in.reason = 2;


        byte[] contentIn = JavaStruct.pack(packet_in);

        ACProtocol msg = new ACProtocol();
        msg.setLen(contentIn.length);
        msg.setContent(contentIn);

        ctx.writeAndFlush(msg);


    }

//    @Override
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
        
        System.out.print("ingress :"+packet_out.ingress_port);
        System.out.print("reason :"+packet_out.reason);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
