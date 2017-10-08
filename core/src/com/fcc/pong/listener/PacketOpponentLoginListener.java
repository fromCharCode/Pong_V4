package com.fcc.pong.listener;

import com.fcc.pong.common.NetworkManager;
import de.zaroxh.network.NetServer;
import de.zaroxh.network.events.PacketListen;
import de.zaroxh.network.events.PacketListener;
import de.zaroxh.network.packets.PacketOpponentLogin;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class PacketOpponentLoginListener extends PacketListener {

    @PacketListen
    public void onOpponentLogin(ChannelHandlerContext ctx, PacketOpponentLogin packet) {
        System.out.println("OPPONENT LOGIN");
        if(NetworkManager.getNettyClientChannel() != null) {
            NetworkManager.setNettyClientChannel(ctx.channel());
            ctx.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> {
                NetServer.getInstance(1337).stop();
            });
        } else {
            ctx.channel().close();
        }
    }

}
