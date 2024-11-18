package com.yungnickyoung.minecraft.ribbits.network.packet;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

/**
 * Packet to toggle the supporter hat for the given player.
 * The client sends this packet to the server to toggle the supporter hat for the player, and the server then
 * forwards the packet to all clients to keep all clients in sync.
 * @param playerUUID The UUID of the player to toggle the supporter hat for
 * @param enabled Whether to enable or disable the supporter hat
 */
public record ToggleSupporterPacket(UUID playerUUID, boolean enabled) implements FabricPacket {
    public static final PacketType<ToggleSupporterPacket> TYPE = PacketType.create(
            RibbitsCommon.id("toggle_supporter_hat"), ToggleSupporterPacket::new);

    public ToggleSupporterPacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readBoolean());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(playerUUID);
        buf.writeBoolean(enabled);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
