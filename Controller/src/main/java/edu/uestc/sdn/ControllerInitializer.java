package edu.uestc.sdn;



import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class ControllerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = SSLContextFactory.getSSLContext("/Controller.jks","uestcsdn","/Controller_trust.jks","uestcsdn").createSSLEngine(); // resource文件夹下的路径必须加 /
        sslEngine.setUseClientMode(false);
        sslEngine.setNeedClientAuth(true);
        pipeline.addFirst(new SslHandler(sslEngine));


        pipeline.addLast(new IdleStateHandler(Controller.heartbeatGap,0,0, TimeUnit.SECONDS));
        
        pipeline.addLast(new ACProtocolDecoder());//解码器
        pipeline.addLast(new ACProtocolEncoder());//编码器
        pipeline.addLast(new ControllerHandler());
        pipeline.addLast(new HeartBeat()); // 再编解码器的后面add才可以用编解码器
    }
}
