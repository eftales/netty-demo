package edu.uestc.sdn;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class AgentInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // pipeline.addLast(new HeartBeat());

        // SSL
        SSLEngine sslEngine = SSLContextFactory.getSSLContext("/"+Agent.hostname+".jks","uestcsdn","/"+Agent.hostname+"_trust.jks","uestcsdn").createSSLEngine();
        sslEngine.setUseClientMode(true);
        // sslEngine.setNeedClientAuth(false);
        pipeline.addFirst(new SslHandler(sslEngine));

        pipeline.addLast(new ACProtocolEncoder()); //加入编码器
        pipeline.addLast(new ACProtocolDecoder()); //加入解码器
        pipeline.addLast(new AgentHandler());
        
    }
}
