package edu.uestc.sdn;



import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class ControllerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ACProtocolDecoder());//解码器
        pipeline.addLast(new ACProtocolEncoder());//编码器
        pipeline.addLast(new ControllerHandler());
    }
}
