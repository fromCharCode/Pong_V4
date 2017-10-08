package de.zaroxh.network.packets;

import de.zaroxh.network.type.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacketSetGamePaused implements Packet {

    private boolean paused;

}
