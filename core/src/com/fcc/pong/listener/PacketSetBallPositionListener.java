package com.fcc.pong.listener;

import com.badlogic.gdx.Gdx;
import com.fcc.pong.script.AiPlayerScript;
import de.zaroxh.network.events.PacketListen;
import de.zaroxh.network.events.PacketListener;
import de.zaroxh.network.packets.PacketSetBallPosition;
import io.netty.channel.ChannelHandlerContext;

public class PacketSetBallPositionListener extends PacketListener {

    @PacketListen
    public void onSetBallPosition(ChannelHandlerContext ctx, PacketSetBallPosition packet) {
        Gdx.app.postRunnable(() -> AiPlayerScript.getInviBall().setPosition(packet.getNewPositionX(), packet.getNewPositionY()));
    }

}
