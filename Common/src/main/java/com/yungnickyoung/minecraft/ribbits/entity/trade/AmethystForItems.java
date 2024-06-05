package com.yungnickyoung.minecraft.ribbits.entity.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public class AmethystForItems implements ItemListing {
    private final Item item;
    private final int countMin;
    private final int countMax;
    private final int maxUses;
    private final float priceMultiplier;
    private final int amethystMin;
    private final int amethystMax;

    public AmethystForItems(ItemLike item, int countMin, int countMax, int amethystMin, int amethystMax, int maxUses) {
        this.item = item.asItem();
        this.countMin = countMin;
        this.countMax = countMax;
        this.amethystMin = amethystMin;
        this.amethystMax = amethystMax;
        this.maxUses = maxUses;
        this.priceMultiplier = 0.05F;
    }

    public MerchantOffer getOffer(Entity entity, RandomSource rand) {
        ItemStack offeredStack = new ItemStack(this.item, rand.nextIntBetweenInclusive(this.countMin, this.countMax));

        return new MerchantOffer(offeredStack, new ItemStack(Items.AMETHYST_SHARD, rand.nextIntBetweenInclusive(this.amethystMin, this.amethystMax)), this.maxUses, 0, this.priceMultiplier);
    }
}
