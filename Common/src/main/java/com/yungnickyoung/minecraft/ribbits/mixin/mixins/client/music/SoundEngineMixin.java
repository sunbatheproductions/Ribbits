package com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.music;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.client.sound.InstrumentSoundInstance;
import com.yungnickyoung.minecraft.ribbits.client.sound.PlayerInstrumentSoundInstance;
import com.yungnickyoung.minecraft.ribbits.client.sound.RibbitInstrumentSoundInstance;
import com.yungnickyoung.minecraft.ribbits.mixin.interfaces.client.IChannelDuck;
import com.yungnickyoung.minecraft.ribbits.mixin.interfaces.client.ISoundEngineDuck;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mixin(SoundEngine.class)
public class SoundEngineMixin implements ISoundEngineDuck {

    @Shadow
    @Final
    private SoundBufferLibrary soundBuffers;

    @Shadow
    @Final
    private List<TickableSoundInstance> tickingSounds;

    @Inject(method = "play",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/SoundBufferLibrary;getCompleteBuffer(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/concurrent/CompletableFuture;",
                    shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    private void ribbits$handleRibbitsOffsetSounds(SoundInstance soundInstance, CallbackInfo ci, WeighedSoundEvents $$1x,
                                                   ResourceLocation $$2x, Sound sound, float $$4x, float $$5x, SoundSource soundSource,
                                                   float $$7x, float $$8x, SoundInstance.Attenuation attenuation, boolean $$10x,
                                                   Vec3 $$11x, boolean $$14, boolean $$15, CompletableFuture $$16, ChannelAccess.ChannelHandle channelAccess) {
        if (soundInstance instanceof InstrumentSoundInstance<?> instrumentSoundInstance) {
            this.playInstrumentSound(sound, channelAccess, instrumentSoundInstance);
            ci.cancel();
        }
    }

    @Unique
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void playInstrumentSound(Sound sound, ChannelAccess.ChannelHandle channelHandle, InstrumentSoundInstance instrumentSoundInstance) {
        // Stop any existing sound with the same entity ID
        List<InstrumentSoundInstance> soundsToStop = this.tickingSounds.stream()
                .filter(instance -> instance instanceof InstrumentSoundInstance)
                .map(instance -> (InstrumentSoundInstance) instance)
                .filter(instance -> instance.isForSameEntity(instrumentSoundInstance))
                .toList();
        soundsToStop.forEach(InstrumentSoundInstance::stopSound);

        // Play the sound with the offset
        this.soundBuffers.getCompleteBuffer(sound.getPath()).thenAccept((soundBuffer) -> {
            channelHandle.execute((channel) -> {
                if (instrumentSoundInstance.getTicksOffset() == -1) { // -1 tick offset means we should use a byte offset instead
                    // Find an existing sound to grab the byte offset from
                    Optional<Integer> existingSoundSourceId = this.tickingSounds.stream()
                            .filter((instance) -> instance instanceof InstrumentSoundInstance<?> && !instance.isStopped() && !(instance.equals(instrumentSoundInstance)))
                            .map(instance -> ((InstrumentSoundInstance<?>) instance).getSourceId())
                            .findAny();

                    if (existingSoundSourceId.isPresent()) {
                        // Play new sound with byte offset from existing sound
                        ((IChannelDuck) channel).ribbits$attachStaticBufferWithByteOffset(instrumentSoundInstance, soundBuffer, existingSoundSourceId.get());
                    } else {
                        if (instrumentSoundInstance instanceof RibbitInstrumentSoundInstance) {
                            // Log an error if we can't find an existing sound to play from for a Ribbit sound.
                            // This should never happen, but if it does, we'll just play the sound from the start.
                            // Note that for player instrument sounds, this is NOT an error, as we expect this to happen
                            // when the player starts playing and no Ribbits or other instruments are playing nearby.
                            RibbitsCommon.LOGGER.error("Tried to play Ribbit sound with byte offset, but no existing sound was found!" +
                                    " Playing from the start instead...");
                        }

                        // Play sound from beginning (0 tick offset)
                        ((IChannelDuck) channel).ribbits$attachStaticBufferWithTickOffset(instrumentSoundInstance, soundBuffer, 0);
                    }
                } else {
                    ((IChannelDuck) channel).ribbits$attachStaticBufferWithTickOffset(instrumentSoundInstance, soundBuffer, instrumentSoundInstance.getTicksOffset());
                }

                channel.play();
            });
        });

        this.tickingSounds.add(instrumentSoundInstance);
    }

    @Unique
    @Override
    public void ribbits$stopRibbitsMusic(int ribbitEntityId) {
        List<RibbitInstrumentSoundInstance> soundsToStop = this.tickingSounds.stream()
                .filter(instance -> instance instanceof RibbitInstrumentSoundInstance)
                .map(instance -> (RibbitInstrumentSoundInstance) instance)
                .filter(instance -> instance.getRibbit().getId() == ribbitEntityId)
                .toList();
        soundsToStop.forEach(RibbitInstrumentSoundInstance::stopSound);
    }

    @Unique
    @Override
    public void ribbits$stopMaraca(int playerId) {
        List<PlayerInstrumentSoundInstance> soundsToStop = this.tickingSounds.stream()
                .filter(instance -> instance instanceof PlayerInstrumentSoundInstance)
                .map(instance -> (PlayerInstrumentSoundInstance) instance)
                .filter(instance -> instance.getPlayer().getId() == playerId)
                .toList();
        soundsToStop.forEach(PlayerInstrumentSoundInstance::stopSound);
    }
}
