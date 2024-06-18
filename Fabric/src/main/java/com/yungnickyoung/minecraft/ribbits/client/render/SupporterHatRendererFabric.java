package com.yungnickyoung.minecraft.ribbits.client.render;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.item.SupporterHatItemFabric;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SupporterHatRendererFabric extends GeoArmorRenderer<SupporterHatItemFabric> {
    public SupporterHatRendererFabric() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(RibbitsCommon.MOD_ID, "supporter_hat")));
    }
}
