package de.zaroxh.network.packets;

import de.zaroxh.network.type.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacketOpponentLogin implements Packet {

    private String name;

}
