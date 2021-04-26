package edu.uestc.sdn;



import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import struct.*;


public class Agent {
    public static String hostname;
    public static void main(String[] args)  throws  Exception{
        if (args.length > 0){
            hostname = args[0];
        }
        else{
            hostname = "sw1";
        }
        System.out.println("This is "+hostname);
        
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new AgentInitializer()); //自定义一个初始化类

            ChannelFuture channelFuture = bootstrap.connect("localhost", 7000).sync();



            Packet_in packet_in = new Packet_in();
            packet_in.ingress_port = 1;
            packet_in.reason = 2;
    
    
            byte[] contentIn = JavaStruct.pack(packet_in);
    
            ACProtocol msg = new ACProtocol();
            msg.setLen(contentIn.length);
            msg.setContent(contentIn);
    
            channelFuture.channel().writeAndFlush(msg);


            channelFuture.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }
    }
}
