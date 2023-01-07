package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class PlacedFeatureModule {
    public static final PlacedFeature VEG_PATCH = register("veg_patch",
            new PlacedFeature(
                    Holder.direct(ConfiguredFeatureModule.VEG_PATCH),
                    List.of()));

    private static PlacedFeature register(String key, PlacedFeature feature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(RibbitsCommon.MOD_ID, key), feature);
    }

    public static void bootstrap() {
    }
}
