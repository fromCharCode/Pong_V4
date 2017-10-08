package de.zaroxh.network;

import de.zaroxh.network.type.Packet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class NetClient extends NetComponent<Packet> {

    private static NetClient instance;

    public static NetClient getInstance(String host, int port, ClassLoader classLoader) {
        if (instance == null) {
            instance = new NetClient(host, port, classLoader);
        }
        return instance;
    }

    public static NetClient getInstance(String host, int port) {
        if (instance == null) {
            instance = new NetClient(host, port, ClassLoader.getSystemClassLoader());
        }
        return instance;
    }

    @Getter
    @Setter
    private String host;

    private EventLoopGroup workerGroup;
    private Channel channel;

    @Setter
    private Runnable close;

    @Setter
    private Consumer<ChannelHandlerContext> start;

    @Getter
    private ClassLoader classLoader;

    private NetClient(String host, int port, ClassLoader classLoader) {
        super(port);
        this.host = host;
        this.classLoader = classLoader;
    }

    @Override
    public ChannelFuture start() {
        this.workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(classLoader)));
                            pipeline.addLast(new NetPacketHandler(NetClient.this));
                        }
                    });
            this.channel = bootstrap.connect(this.host, this.getPort()).sync().channel();
            ChannelFuture f = channel.closeFuture().addListener(future -> NetClient.this.workerGroup.shutdownGracefully());
            if(this.close != null) {
                f.addListener(future -> close.run());
            }
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handleChannelActive(ChannelHandlerContext ctx) {
        this.start.accept(ctx);
    }

}
