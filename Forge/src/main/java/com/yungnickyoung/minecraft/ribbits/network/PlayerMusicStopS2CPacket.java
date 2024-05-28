package com.yungnickyoung.minecraft.ribbits.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerMusicStopS2CPacket {
    private final int performerId;

    public PlayerMusicStopS2CPacket(int performerId) {
        this.performerId = performerId;
    }

    /**
     * Decoder
     */
    public PlayerMusicStopS2CPacket(FriendlyByteBuf buf) {
        this.performerId = buf.readInt();
    }

    /**
     * Encoder
     */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.performerId);
    }

    /**
     * Handler
     */
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure this is only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerForge.handleStopPlayerInstrument(this, ctx))
        );
        ctx.get().setPacketHandled(true);
        return true;
    }

    public int getPerformerId() {
        return this.performerId;
    }
}
