package com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.maraca;

import com.yungnickyoung.minecraft.ribbits.client.render.MaracaInHandRenderer;
import com.yungnickyoung.minecraft.ribbits.module.ItemModule;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    /**
     * Replaces the model of the maraca with a flat texture when rendering in the GUI.
     */
    @ModifyVariable(method = "render", at = @At(value = "HEAD"), argsOnly = true)
    private BakedModel ribbits$renderFlatTextureInGuiForMaraca(BakedModel original, ItemStack itemStack, ItemDisplayContext ctx) {
        boolean isGuiModel = ctx == ItemDisplayContext.GUI || ctx == ItemDisplayContext.GROUND || ctx == ItemDisplayContext.FIXED;
        if (isGuiModel) {
            if (itemStack.is(ItemModule.MARACA.get())) {
                return this.itemModelShaper.getModelManager().getModel(MaracaInHandRenderer.MARACA_MODEL);
            }
        }
        return original;
    }

    /**
     * Renders the proper 3D model for the Maraca.
     */
    @ModifyVariable(method = "getModel", at = @At(value = "STORE"))
    private BakedModel ribbits$renderModelInHandForMaraca(BakedModel original, ItemStack itemStack) {
        if (itemStack.is(ItemModule.MARACA.get())) {
            return this.itemModelShaper.getModelManager().getModel(MaracaInHandRenderer.MARACA_IN_HAND_MODEL);
        }
        return original;
    }
}
