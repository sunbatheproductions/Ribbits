package com.yungnickyoung.minecraft.ribbits;

import com.yungnickyoung.minecraft.ribbits.client.RibbitsForgeClient;
import com.yungnickyoung.minecraft.ribbits.item.RibbitSpawnEggDispenseItemBehaviorForge;
import com.yungnickyoung.minecraft.ribbits.module.ItemModule;
import com.yungnickyoung.minecraft.ribbits.player.PlayerInstrumentTracker;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RibbitsCommon.MOD_ID)
public class RibbitsForge {
    public RibbitsForge() {
        RibbitsCommon.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> RibbitsForgeClient::init);
        MinecraftForge.EVENT_BUS.addListener(RibbitsForge::onServerTickStart);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(RibbitsForge::onCommonSetup);
    }

    private static void onServerTickStart(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerInstrumentTracker.onServerTick();
        }
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        // Custom dispenser behavior for Ribbit spawn eggs
        DispenseItemBehavior ribbitSpawnEggDispenseItemBehavior = new RibbitSpawnEggDispenseItemBehaviorForge();
        DispenserBlock.registerBehavior(ItemModule.RIBBIT_NITWIT_SPAWN_EGG.get(), ribbitSpawnEggDispenseItemBehavior);
        DispenserBlock.registerBehavior(ItemModule.RIBBIT_FISHERMAN_SPAWN_EGG.get(), ribbitSpawnEggDispenseItemBehavior);
        DispenserBlock.registerBehavior(ItemModule.RIBBIT_GARDENER_SPAWN_EGG.get(), ribbitSpawnEggDispenseItemBehavior);
        DispenserBlock.registerBehavior(ItemModule.RIBBIT_MERCHANT_SPAWN_EGG.get(), ribbitSpawnEggDispenseItemBehavior);
        DispenserBlock.registerBehavior(ItemModule.RIBBIT_SORCERER_SPAWN_EGG.get(), ribbitSpawnEggDispenseItemBehavior);
    }
}