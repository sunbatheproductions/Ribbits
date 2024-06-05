package com.yungnickyoung.minecraft.ribbits.entity.trade;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;

public class PotionForAmethyst implements ItemListing {

    private final ItemStack toItem;
    private final int count;
    private final int amethystCostMin;
    private final int amethystCostMax;
    private final int maxUses;
    private final float priceMultiplier;

    @Nullable
    private final Potion potionType;

    public PotionForAmethyst(ItemLike toItem, @Nullable Potion potionType, int count, int amethystCostMin, int amethystCostMax, int maxUses) {
        this.toItem = new ItemStack(toItem);
        this.count = count;
        this.amethystCostMin = amethystCostMin;
        this.amethystCostMax = amethystCostMax;
        this.maxUses = maxUses;
        this.priceMultiplier = 0.05F;

        this.potionType = potionType;
    }

    public MerchantOffer getOffer(Entity entity, RandomSource rand) {
        ItemStack amethystStack = new ItemStack(Items.AMETHYST_SHARD, rand.nextIntBetweenInclusive(this.amethystCostMin, this.amethystCostMax));

        ItemStack potionStack;
        if (this.potionType == null) {
            List<Potion> potionList = BuiltInRegistries.POTION.stream().filter((potion) ->
                    !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion)).toList();

            Potion potionEffect = potionList.get(rand.nextInt(potionList.size()));
            potionStack = PotionUtils.setPotion(new ItemStack(this.toItem.getItem(), this.count), potionEffect);
        } else {
            potionStack = PotionUtils.setPotion(new ItemStack(this.toItem.getItem(), this.count), this.potionType);
        }

        return new MerchantOffer(amethystStack, potionStack, this.maxUses, 0, this.priceMultiplier);
    }
}
