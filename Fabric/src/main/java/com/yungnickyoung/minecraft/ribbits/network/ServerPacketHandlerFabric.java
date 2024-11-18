package com.yungnickyoung.minecraft.ribbits.network;

import com.yungnickyoung.minecraft.ribbits.network.packet.ToggleSupporterPacket;
import com.yungnickyoung.minecraft.ribbits.supporters.SupportersListServer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class ServerPacketHandlerFabric {
    public static void receiveToggleSupporterHat(ToggleSupporterPacket packet, ServerPlayer player, PacketSender responseSender) {
        // Update the player's supporter hat status on the server
        SupportersListServer.toggleSupporterHat(packet.playerUUID(), packet.enabled());

        // Forward the packet to all clients
        if (player.getServer() != null) {
            PlayerLookup.all(player.getServer()).forEach(p -> ServerPlayNetworking.send(p, packet));
        }
    }
}
