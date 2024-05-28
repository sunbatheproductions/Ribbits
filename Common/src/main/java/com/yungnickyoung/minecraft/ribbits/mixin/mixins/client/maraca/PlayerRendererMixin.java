package com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.maraca;

import com.yungnickyoung.minecraft.ribbits.module.ItemModule;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public PlayerRendererMixin(EntityRendererProvider.Context $$0, PlayerModel<AbstractClientPlayer> $$1, float $$2) {
        super($$0, $$1, $$2);
    }

//    @Inject(method = "getArmPose", at = @At("HEAD"))
//    private static void ribbits$maracaArmPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
//        ItemStack itemInHand = player.getItemInHand(hand);
//        if (player.getUsedItemHand() == hand && player.getUseItemRemainingTicks() > 0) {
//            UseAnim $$3 = $$2.getUseAnimation();
//            if ($$3 == UseAnim.BLOCK) {
//                return HumanoidModel.ArmPose.BLOCK;
//            }
//        }
//    }

    @Inject(method = "setModelProperties", at = @At("RETURN"), cancellable = true)
    private void ribbits$maracaSetModelProperties(AbstractClientPlayer player, CallbackInfo ci) {
//        PlayerModel<AbstractClientPlayer> playerModel = this.getModel();
//        if (!player.isSpectator()) {
//            ItemStack itemInHand = player.getItemInHand($$1);
//            if (!itemInHand.isEmpty() && player.getUsedItemHand()ItemModule.MARACA.get() && player.getUseItemRemainingTicks() > 0) {
//                if ($$0.getUsedItemHand() == $$1 && $$0.getUseItemRemainingTicks() > 0) {
//                    UseAnim $$3 = itemInHand.getUseAnimation();
//                    if ($$3 == UseAnim.BLOCK) {
//                        return HumanoidModel.ArmPose.BLOCK;
//                    }
//
//                    if ($$3 == UseAnim.BOW) {
//                        return HumanoidModel.ArmPose.BOW_AND_ARROW;
//                    }
//
//                    if ($$3 == UseAnim.SPEAR) {
//                        return HumanoidModel.ArmPose.THROW_SPEAR;
//                    }
//
//                    if ($$3 == UseAnim.CROSSBOW && $$1 == $$0.getUsedItemHand()) {
//                        return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
//                    }
//
//                    if ($$3 == UseAnim.SPYGLASS) {
//                        return HumanoidModel.ArmPose.SPYGLASS;
//                    }
//
//                    if ($$3 == UseAnim.TOOT_HORN) {
//                        return HumanoidModel.ArmPose.TOOT_HORN;
//                    }
//
//                    if ($$3 == UseAnim.BRUSH) {
//                        return HumanoidModel.ArmPose.BRUSH;
//                    }
//                } else if (!$$0.swinging && itemInHand.is(Items.CROSSBOW) && CrossbowItem.isCharged(itemInHand)) {
//                    return HumanoidModel.ArmPose.CROSSBOW_HOLD;
//                }
//
//                return HumanoidModel.ArmPose.ITEM;
//            }
//
//
//            if (player.getMainArm() == HumanoidArm.RIGHT) {
//                HumanoidModel.ArmPose mainHandPose = getArmPose($$0, InteractionHand.MAIN_HAND);
//                HumanoidModel.ArmPose offHandPose = getArmPose($$0, InteractionHand.OFF_HAND);
//
//                playerModel.rightArmPose = mainHandPose;
//                playerModel.leftArmPose = offHandPose;
//            } else {
//                HumanoidModel.ArmPose mainHandPose = getArmPose($$0, InteractionHand.MAIN_HAND);
//                HumanoidModel.ArmPose offHandPose = getArmPose($$0, InteractionHand.OFF_HAND);
//
//                playerModel.rightArmPose = offHandPose;
//                playerModel.leftArmPose = mainHandPose;
//            }
//        }
    }
}
