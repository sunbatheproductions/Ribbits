package com.yungnickyoung.minecraft.ribbits.services;

import com.yungnickyoung.minecraft.ribbits.network.packet.ToggleSupporterPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

import java.util.UUID;

public class FabricSupporterHelper implements ISupporterHelper {
    @Override
    public void toggleSupporterHatNotifyServer(boolean enabled) {
        // Ensure the player UUID is valid
        UUID playerUUID = Minecraft.getInstance().getUser().getProfileId();
        if (playerUUID == null) return;

        // Only send packet if the player is connected to a server
        if (Minecraft.getInstance().getConnection() == null) return;

        ToggleSupporterPacket packet = new ToggleSupporterPacket(playerUUID, enabled);
        ClientPlayNetworking.send(packet);
    }
}
