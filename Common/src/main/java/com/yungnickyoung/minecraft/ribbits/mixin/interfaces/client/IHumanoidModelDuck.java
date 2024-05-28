package com.yungnickyoung.minecraft.ribbits.mixin.interfaces.client;

public interface IHumanoidModelDuck {
    boolean ribbits$isRightHandUsingMaraca();
    boolean ribbits$isLeftHandUsingMaraca();

    void ribbits$setRightHandUsingMaraca(boolean usingMaraca);
    void ribbits$setLeftHandUsingMaraca(boolean usingMaraca);
}
