package com.yungnickyoung.minecraft.ribbits;

import com.yungnickyoung.minecraft.ribbits.client.RibbitsForgeClient;
import com.yungnickyoung.minecraft.ribbits.player.PlayerInstrumentTracker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(RibbitsCommon.MOD_ID)
public class RibbitsForge {
    public RibbitsForge() {
        RibbitsCommon.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> RibbitsForgeClient::init);
        MinecraftForge.EVENT_BUS.addListener(this::onServerTickStart);
    }

    private void onServerTickStart(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerInstrumentTracker.onServerTick();
        }
    }
}