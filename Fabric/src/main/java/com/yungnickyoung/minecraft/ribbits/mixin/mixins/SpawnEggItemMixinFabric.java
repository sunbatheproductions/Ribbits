package com.yungnickyoung.minecraft.ribbits.mixin.mixins;

import com.google.common.collect.Iterables;
import com.yungnickyoung.minecraft.ribbits.module.ItemModule;
import net.minecraft.world.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixinFabric {
    /**
     * Add custom spawn eggs to the list of spawn eggs.
     * This is necessary for them to render with the correct colors.
     */
    @Inject(method = "eggs", at = @At("RETURN"), cancellable = true)
    private static void ribbits$spawnEggColors(CallbackInfoReturnable<Iterable<SpawnEggItem>> cir) {
        Iterable<SpawnEggItem> original = cir.getReturnValue();
        List<SpawnEggItem> allEggs = new ArrayList<>();
        original.forEach(allEggs::add);
        allEggs.add((SpawnEggItem) ItemModule.RIBBIT_FISHERMAN_SPAWN_EGG.get());
        allEggs.add((SpawnEggItem) ItemModule.RIBBIT_GARDENER_SPAWN_EGG.get());
        allEggs.add((SpawnEggItem) ItemModule.RIBBIT_MERCHANT_SPAWN_EGG.get());
        allEggs.add((SpawnEggItem) ItemModule.RIBBIT_NITWIT_SPAWN_EGG.get());
        allEggs.add((SpawnEggItem) ItemModule.RIBBIT_SORCERER_SPAWN_EGG.get());
        cir.setReturnValue(Iterables.unmodifiableIterable(allEggs));
    }
}
