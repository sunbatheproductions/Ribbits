package com.yungnickyoung.minecraft.ribbits.network.packet;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.util.BufferUtils;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.UUID;

/**
 * Packet sent from the server to the client to request the given client's supporter hat state.
 * Includes a list of all players with the supporter hat enabled on the server.
 * @param enabledSupporterHatPlayers A list of UUIDs of players on the server with the supporter hat enabled
 */
public record RequestSupporterHatStatePacket(List<UUID> enabledSupporterHatPlayers) implements FabricPacket {
    public static final PacketType<RequestSupporterHatStatePacket> TYPE = PacketType.create(
            RibbitsCommon.id("request_supporter_hat_state"), RequestSupporterHatStatePacket::new);

    public RequestSupporterHatStatePacket(FriendlyByteBuf buf) {
        this(BufferUtils.readUUIDList(buf));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        BufferUtils.writeUUIDList(enabledSupporterHatPlayers, buf);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
