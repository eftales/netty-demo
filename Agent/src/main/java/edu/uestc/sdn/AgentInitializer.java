package edu.uestc.sdn;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class AgentInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ACProtocolEncoder()); //加入编码器
        pipeline.addLast(new ACProtocolDecoder()); //加入解码器
        pipeline.addLast(new AgentHandler());
    }
}
