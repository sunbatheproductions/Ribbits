package com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.maraca;

import com.yungnickyoung.minecraft.ribbits.client.RibbitsCommonClient;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow protected abstract void loadTopLevel(ModelResourceLocation $$0);

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;

    @Shadow public abstract UnbakedModel getModel(ResourceLocation $$0);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void ribbits$addMaracaModelToBakery(CallbackInfo ci) {
        this.loadTopLevel(RibbitsCommonClient.MARACA_IN_HAND_MODEL);
        UnbakedModel unbakedModel = this.topLevelModels.get(RibbitsCommonClient.MARACA_IN_HAND_MODEL);
        unbakedModel.resolveParents(this::getModel);
    }
}
