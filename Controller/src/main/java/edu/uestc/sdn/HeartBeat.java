package edu.uestc.sdn;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.buffer.Unpooled;
import struct.*;


public class HeartBeat extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent) {

            //将  evt 向下转型 IdleStateEvent
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                  eventType = "读空闲";
                  break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + eventType);
            Packet_in packet_out = new Packet_in();
            packet_out.ingress_port = 0;
            packet_out.reason = 0;
    
    
            byte[] contentOut = JavaStruct.pack(packet_out);
    
            ACProtocol msgOut = new ACProtocol();
            msgOut.setLen(contentOut.length);
            msgOut.setContent(contentOut);
    
            ctx.writeAndFlush(msgOut);

            //如果发生空闲，我们关闭通道
           // ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }
    
}
