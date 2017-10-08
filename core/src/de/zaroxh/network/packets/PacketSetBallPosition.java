package de.zaroxh.network.packets;

import de.zaroxh.network.type.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacketSetBallPosition implements Packet {

    private float newPositionX;
    private float newPositionY;

}
