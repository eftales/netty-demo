package edu.uestc.sdn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;



import struct.*;

//处理业务的handler
public class ControllerHandler extends SimpleChannelInboundHandler<ACProtocol>{

    private static ChannelGroup  channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
        
        System.out.println("ingress :"+packet_in.ingress_port);
        System.out.println("reason :"+packet_in.reason);


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

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[switch]" + ctx.channel().remoteAddress() + " connected.");
        channelGroup.add(ctx.channel());

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[switch]" + ctx.channel().remoteAddress() + " disconnected.");

    }


}
