package com.fcc.pong.listener;

import com.badlogic.gdx.Gdx;
import com.fcc.pong.script.MultiplayerOpponentScript;
import de.zaroxh.network.events.PacketListen;
import de.zaroxh.network.events.PacketListener;
import de.zaroxh.network.packets.PacketSetPaddlePosition;
import io.netty.channel.ChannelHandlerContext;

public class PacketSetPaddlePositionListener extends PacketListener {

    @PacketListen
    public void onSetPaddlePosition(ChannelHandlerContext ctx, PacketSetPaddlePosition packet) {
        Gdx.app.postRunnable(() -> MultiplayerOpponentScript.setYPosition(packet.getNewPosition()));
    }

}
