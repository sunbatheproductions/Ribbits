package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.item.MaracaItem;
import com.yungnickyoung.minecraft.ribbits.services.Services;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;

@AutoRegister(RibbitsCommon.MOD_ID)
public class ItemModule {
    @AutoRegister("giant_lilypad")
    public static final AutoRegisterItem GIANT_LILYPAD = AutoRegisterItem.of(() -> new PlaceOnWaterBlockItem(BlockModule.GIANT_LILYPAD.get(), new Item.Properties()));

    @AutoRegister("ribbit_nitwit_spawn_egg")
    public static final AutoRegisterItem RIBBIT_NITWIT_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getRibbitSpawnEggItem(RibbitProfessionModule.NITWIT, 0xb3c35b, 0x769b4f));

    @AutoRegister("ribbit_fisherman_spawn_egg")
    public static final AutoRegisterItem RIBBIT_FISHERMAN_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getRibbitSpawnEggItem(RibbitProfessionModule.FISHERMAN, 0xb3c35b, 0x956c41));

    @AutoRegister("ribbit_gardener_spawn_egg")
    public static final AutoRegisterItem RIBBIT_GARDENER_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getRibbitSpawnEggItem(RibbitProfessionModule.GARDENER, 0xb3c35b, 0x6b4434));

    @AutoRegister("ribbit_merchant_spawn_egg")
    public static final AutoRegisterItem RIBBIT_MERCHANT_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getRibbitSpawnEggItem(RibbitProfessionModule.MERCHANT, 0xb3c35b, 0x5b3f28));

    @AutoRegister("ribbit_sorcerer_spawn_egg")
    public static final AutoRegisterItem RIBBIT_SORCERER_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getRibbitSpawnEggItem(RibbitProfessionModule.SORCERER, 0xb3c35b, 0x774d7e));

    @AutoRegister("maraca")
    public static final AutoRegisterItem MARACA = AutoRegisterItem.of(() -> new MaracaItem(new Item.Properties().stacksTo(1)));

    @AutoRegister("_ignored")
    public static void registerCompostables() {
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.SWAMP_DAISY.get().asItem(), 0.65F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.GIANT_LILYPAD.get().asItem(), 0.65F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.UMBRELLA_LEAF.get().asItem(), 0.65F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.TOADSTOOL.get().asItem(), 0.65F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.TOADSTOOL_STEM.get().asItem(), 0.85F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.BROWN_TOADSTOOL.get().asItem(), 0.85F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.RED_TOADSTOOL.get().asItem(), 0.85F);
    }
}
