package com.fcc.pong.common;

import com.badlogic.gdx.Gdx;
import com.fcc.pong.PongGame;
import com.fcc.pong.screen.game.GameScreen;
import com.fcc.pong.screen.menu.MultiPlayerMenu;
import de.zaroxh.network.NetClient;
import de.zaroxh.network.NetServer;
import de.zaroxh.network.type.Packet;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

public class NetworkManager {

    @Getter
    private static boolean isHost = false;

    @Getter
    @Setter
    private static Channel nettyClientChannel;

    @Getter
    @Setter
    private static Channel nettyServerChannel;

    public static void sendPacket(Packet packet) {
        if(nettyClientChannel != null) {
            nettyClientChannel.writeAndFlush(packet);
        } else if(nettyServerChannel != null) {
            nettyServerChannel.writeAndFlush(packet);
        }
    }

    public static void multiPlayerConnect(String ip) {
        NetClient client = NetClient.getInstance(ip, 1337, PongGame.getInstance().getClass().getClassLoader());
        client.setClose(() -> {
            Gdx.app.postRunnable(() -> PongGame.getInstance().setScreen(new MultiPlayerMenu(MultiPlayerMenu.getAmount(), "CONNECTION CLOSED", MultiPlayerMenu.getSoundController())));
        });
        client.setStart((ctx) -> {
            nettyServerChannel = ctx.channel();
            Gdx.app.postRunnable(() ->
                PongGame.getInstance().setScreen(
                        new GameScreen(
                                MultiPlayerMenu.getAmount(),
                                MultiPlayerMenu.getSoundController()))
            );
        });
        client.registerListenersInPackage("com.fcc.pong.listener");
        client.start();
    }

    public static void multiPlayerHost() {
        isHost = true;
        NetServer server = NetServer.getInstance(1337);
        server.setClose(() -> {
            Gdx.app.postRunnable(() -> PongGame.getInstance().setScreen(new MultiPlayerMenu(MultiPlayerMenu.getAmount(), "SERVER CLOSED", MultiPlayerMenu.getSoundController())));
        });
        server.registerListenersInPackage("com.fcc.pong.listener");
        server.start();
        PongGame.getInstance().setScreen(
                new GameScreen(
                        MultiPlayerMenu.getAmount(),
                        MultiPlayerMenu.getSoundController()));
    }

}
