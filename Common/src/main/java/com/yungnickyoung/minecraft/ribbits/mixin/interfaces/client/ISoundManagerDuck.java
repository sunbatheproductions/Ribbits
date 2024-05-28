package com.yungnickyoung.minecraft.ribbits.mixin.interfaces.client;

import com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.music.SoundManagerMixin;

/**
 * Duck interface for attaching data to Minecraft's {@link net.minecraft.client.sounds.SoundManager} class.
 * @see SoundManagerMixin
 */
public interface ISoundManagerDuck {
    void ribbits$stopRibbitsMusic(int ribbitEntityId);
    void ribbits$stopMaraca(int playerId);
}
