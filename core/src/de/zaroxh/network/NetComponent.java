package de.zaroxh.network;

import com.google.common.reflect.ClassPath;
import de.zaroxh.network.events.ListenerRegistry;
import de.zaroxh.network.events.PacketListener;
import de.zaroxh.network.type.PacketRegistry;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public abstract class NetComponent<T> {

    @Getter
    private PacketRegistry<T> packetRegistry;
    @Getter
    private ListenerRegistry<T> listenerRegistry;

    @Getter
    @Setter
    private int port;

    public NetComponent(int port) {
        this.port = port;
        this.packetRegistry = new PacketRegistry<>();
        this.listenerRegistry = new ListenerRegistry<>();
    }

    public void handleChannelActive(ChannelHandlerContext ctx) {}

    public void registerListenersInPackage(String packageName) {
        try {
            System.out.println("Register listener classes in Package " + packageName);
            for (ClassPath.ClassInfo classInfo : ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses(packageName)) {
                Class clazz = Class.forName(classInfo.getName());
                System.out.println("[1] Register netty listener: "  + clazz.getSimpleName());
                if (PacketListener.class.isAssignableFrom(clazz)) {
                    System.out.println("[2] Register netty listener: "  + clazz.getSimpleName());
                    listenerRegistry.register((PacketListener) clazz.newInstance());
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public abstract ChannelFuture start();

}
