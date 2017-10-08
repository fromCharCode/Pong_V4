package de.zaroxh.network;

import de.zaroxh.network.type.Packet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

public class NetServer extends NetComponent<Packet> {

    private static ConcurrentHashMap<Integer, NetServer> instances = new ConcurrentHashMap<>();

    public static NetServer getInstance(int port) {
        if (!instances.containsKey(port)) {
            instances.put(port, new NetServer(port));
        }
        return instances.get(port);
    }

    private EventLoopGroup bossGroup, workerGroup;
    private Channel channel;

    @Setter
    private Runnable close;

    private NetServer(int port) {
        super(port);
    }

    @Override
    public ChannelFuture start() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new NetPacketHandler(NetServer.this));
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
                    //.childOption(ChannelOption.SO_BACKLOG, 50);

            this.channel = bootstrap.bind(this.getPort()).channel();
            ChannelFuture future = this.channel.closeFuture().addListener((ChannelFutureListener) (channelFuture) -> {
                NetServer.this.bossGroup.shutdownGracefully();
                NetServer.this.workerGroup.shutdownGracefully();
            });
            if (this.close != null) {
                return future.addListener((ChannelFutureListener) channelFuture -> this.close.run());
            }
            return future;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stop() {
        this.channel.close();
    }

}
