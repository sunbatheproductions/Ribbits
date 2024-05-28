package com.yungnickyoung.minecraft.ribbits;

import com.yungnickyoung.minecraft.ribbits.player.PlayerInstrumentTracker;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import software.bernie.geckolib.GeckoLib;

public class RibbitsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        RibbitsCommon.init();
        ServerTickEvents.START_SERVER_TICK.register(server -> PlayerInstrumentTracker.onServerTick());
    }
}
