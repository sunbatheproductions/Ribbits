package com.yungnickyoung.minecraft.ribbits.network;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.client.RibbitsCommonClient;
import com.yungnickyoung.minecraft.ribbits.client.sound.PlayerInstrumentSoundInstance;
import com.yungnickyoung.minecraft.ribbits.client.sound.RibbitInstrumentSoundInstance;
import com.yungnickyoung.minecraft.ribbits.client.supporters.RibbitOptionsJSON;
import com.yungnickyoung.minecraft.ribbits.client.supporters.SupportersListClient;
import com.yungnickyoung.minecraft.ribbits.data.RibbitInstrument;
import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.mixin.interfaces.client.ISoundManagerDuck;
import com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.accessor.ClientLevelAccessor;
import com.yungnickyoung.minecraft.ribbits.module.RibbitInstrumentModule;
import com.yungnickyoung.minecraft.ribbits.module.SoundModule;
import com.yungnickyoung.minecraft.ribbits.network.packet.RequestSupporterHatStatePacket;
import com.yungnickyoung.minecraft.ribbits.network.packet.ToggleSupporterPacket;
import com.yungnickyoung.minecraft.ribbits.services.Services;
import com.yungnickyoung.minecraft.ribbits.util.BufferUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientPacketHandlerFabric {
    public static void receiveStartSingle(Minecraft client,
                                          ClientPacketListener clientPacketListener,
                                          FriendlyByteBuf buf,
                                          PacketSender responseSender) {
        UUID entityId = buf.readUUID();
        RibbitEntity ribbit = (RibbitEntity) ((ClientLevelAccessor) client.level).callGetEntities().get(entityId);

        RibbitInstrument instrument = RibbitInstrumentModule.getInstrument(buf.readResourceLocation());
        int tickOffset = buf.readInt();

        if (ribbit == null) {
            RibbitsCommon.LOGGER.error("Received Start Music packet for a ribbit with UUID {} that doesn't exist!", entityId);
            return;
        }

        if (instrument == null) {
            RibbitsCommon.LOGGER.error("Tried to play music for a ribbit with null instrument!");
            return;
        }

        if (instrument == RibbitInstrumentModule.NONE) {
            RibbitsCommon.LOGGER.error("Tried to play music for a ribbit with NONE instrument!");
            return;
        }
        SoundEvent instrumentSoundEvent = instrument.getSoundEvent();

        client.execute(() -> {
            Minecraft.getInstance().getSoundManager().play(new RibbitInstrumentSoundInstance(ribbit, tickOffset, instrumentSoundEvent));
        });
    }

    public static void receiveStartAll(Minecraft client,
                                       ClientPacketListener clientPacketListener,
                                       FriendlyByteBuf buf,
                                       PacketSender responseSender) {
        List<UUID> entityIds = BufferUtils.readUUIDList(buf);
        List<ResourceLocation> instrumentIds = BufferUtils.readResourceLocationList(buf);
        int tickOffset = buf.readInt();

        if (entityIds.size() != instrumentIds.size()) {
            RibbitsCommon.LOGGER.error("Received Start Music All packet with {} ribbits and {} instruments!", entityIds.size(), instrumentIds.size());
            return;
        }

        for (int i = 0; i < entityIds.size(); i++) {
            RibbitEntity ribbit = (RibbitEntity) ((ClientLevelAccessor) client.level).callGetEntities().get(entityIds.get(i));
            if (ribbit == null) {
                RibbitsCommon.LOGGER.error("Received Start Music All packet for a ribbit with UUID {} that doesn't exist!", entityIds.get(i));
                return;
            }

            RibbitInstrument instrument = RibbitInstrumentModule.getInstrument(instrumentIds.get(i));
            if (instrument == null) {
                RibbitsCommon.LOGGER.error("Tried to play music in receiveStartAll for a ribbit with null instrument!");
                return;
            }

            if (instrument == RibbitInstrumentModule.NONE) {
                RibbitsCommon.LOGGER.error("Tried to play music in receiveStartAll for a ribbit with NONE instrument!");
                return;
            }
            SoundEvent instrumentSoundEvent = instrument.getSoundEvent();

            client.execute(() -> {
                Minecraft.getInstance().getSoundManager().play(new RibbitInstrumentSoundInstance(ribbit, tickOffset, instrumentSoundEvent));
            });
        }
    }

    public static void receiveStop(Minecraft client,
                                   ClientPacketListener clientPacketListener,
                                   FriendlyByteBuf buf,
                                   PacketSender responseSender) {
        UUID entityId = buf.readUUID();
        RibbitEntity ribbit = (RibbitEntity) ((ClientLevelAccessor) client.level).callGetEntities().get(entityId);

        if (ribbit == null) {
            RibbitsCommon.LOGGER.error("Received Stop Music packet for a ribbit with UUID {} that doesn't exist!", entityId);
            return;
        }

        client.execute(() -> {
            ((ISoundManagerDuck) Minecraft.getInstance().getSoundManager()).ribbits$stopRibbitsMusic(entityId);
        });
    }

    public static void receiveStartMaraca(Minecraft client,
                                          ClientPacketListener clientPacketListener,
                                          FriendlyByteBuf buf,
                                          PacketSender responseSender) {
        UUID performerId = buf.readUUID();
        Entity performer = ((ClientLevelAccessor) client.level).callGetEntities().get(performerId);

        if (performer == null) {
            RibbitsCommon.LOGGER.error("Received Start Maraca packet for Player performer with UUID {} that doesn't exist!", performerId);
            return;
        } else if (!(performer instanceof Player)) {
            RibbitsCommon.LOGGER.error("Received Start Maraca packet for non-Player performer with UUID {}!", performerId);
            return;
        }

        client.execute(() -> {
            Minecraft.getInstance().getSoundManager().play(new PlayerInstrumentSoundInstance((Player) performer, -1, SoundModule.MUSIC_MARACA.get()));
        });
    }

    public static void receiveStopMaraca(Minecraft client,
                                         ClientPacketListener clientPacketListener,
                                         FriendlyByteBuf buf,
                                         PacketSender responseSender) {
        UUID performerId = buf.readUUID();
        Entity performer = ((ClientLevelAccessor) client.level).callGetEntities().get(performerId);

        if (performer == null) {
            RibbitsCommon.LOGGER.error("Received Stop Maraca packet for Player performer with UUID {} that doesn't exist!", performerId);
            return;
        } else if (!(performer instanceof Player)) {
            RibbitsCommon.LOGGER.error("Received Stop Maraca packet for non-Player performer with UUID {}!", performerId);
            return;
        }

        client.execute(() -> {
            ((ISoundManagerDuck) Minecraft.getInstance().getSoundManager()).ribbits$stopMaraca(performerId);
        });
    }

    public static void receiveToggleSupporterHat(ToggleSupporterPacket packet, LocalPlayer player, PacketSender responseSender) {
        // Update the player's supporter hat status on the client
        SupportersListClient.toggleSupporterHat(packet.playerUUID(), packet.enabled());
    }

    public static void receiveSupporterHatStateRequest(RequestSupporterHatStatePacket packet, LocalPlayer player, PacketSender responseSender) {
        // Populate local supporter hat list with the list from the server
        SupportersListClient.clear();
        packet.enabledSupporterHatPlayers().forEach(playerUUID -> SupportersListClient.toggleSupporterHat(playerUUID, true));

        // Send a ToggleSupporterPacket back to the server with this player's supporter hat state
        Services.SUPPORTER_HELPER.toggleSupporterHatNotifyServer(RibbitOptionsJSON.get().isSupporterHatEnabled());
    }
}
