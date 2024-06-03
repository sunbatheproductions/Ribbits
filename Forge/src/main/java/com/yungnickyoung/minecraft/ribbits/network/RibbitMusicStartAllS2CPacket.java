package com.yungnickyoung.minecraft.ribbits.network;

import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.util.BufferUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class RibbitMusicStartAllS2CPacket {
    private final List<UUID> ribbitIds;
    private final int tickOffset;

    public RibbitMusicStartAllS2CPacket(RibbitEntity masterRibbit, int tickOffset) {
        List<UUID> ribbitIds = new ArrayList<>();
        ribbitIds.add(masterRibbit.getUUID());
        ribbitIds.addAll(masterRibbit.getRibbitsPlayingMusic().stream().map(RibbitEntity::getUUID).toList());
        this.ribbitIds = ribbitIds;
        this.tickOffset = tickOffset;
    }

    /**
     * Decoder
     */
    public RibbitMusicStartAllS2CPacket(FriendlyByteBuf buf) {
        this.ribbitIds = BufferUtils.readUUIDList(buf);
        this.tickOffset = buf.readInt();
    }

    /**
     * Encoder
     */
    public void toBytes(FriendlyByteBuf buf) {
        BufferUtils.writeUUIDList(this.ribbitIds, buf);
        buf.writeInt(tickOffset);
    }

    /**
     * Handler
     */
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure this is only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerForge.handleStartAllRibbitInstruments(this, ctx))
        );
        ctx.get().setPacketHandled(true);
        return true;
    }

    public List<UUID> getRibbitIds() {
        return this.ribbitIds;
    }

    public int getTickOffset() {
        return this.tickOffset;
    }
}
