package de.zaroxh.network;

import de.zaroxh.network.type.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

public class NetPacketHandler extends ChannelInboundHandlerAdapter {

    private final NetComponent<Packet> component;

    public NetPacketHandler(NetComponent component) {
        this.component = component;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.component.handleChannelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        component.getListenerRegistry().callEvent(ctx, (Packet) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause.getClass() == IOException.class) {
            //return;
        }
        cause.printStackTrace();
    }
}
