package com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.maraca;

import com.yungnickyoung.minecraft.ribbits.mixin.interfaces.client.IHumanoidModelDuck;
import net.minecraft.client.model.HumanoidModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin implements IHumanoidModelDuck {
    @Unique
    private boolean isUsingMaracaInRightHand = false;

    @Unique
    private boolean isUsingMaracaInLeftHand = false;

    @Override
    public boolean ribbits$isRightHandUsingMaraca() {
        return this.isUsingMaracaInRightHand;
    }

    @Override
    public boolean ribbits$isLeftHandUsingMaraca() {
        return this.isUsingMaracaInLeftHand;
    }

    @Override
    public void ribbits$setRightHandUsingMaraca(boolean usingMaraca) {
        this.isUsingMaracaInRightHand = usingMaraca;
    }

    @Override
    public void ribbits$setLeftHandUsingMaraca(boolean usingMaraca) {
        this.isUsingMaracaInLeftHand = usingMaraca;
    }
}
