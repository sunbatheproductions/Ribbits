package com.yungnickyoung.minecraft.ribbits.entity.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;

public class ItemsAndAmethystsToItems implements ItemListing {
    private final ItemStack fromItem;
    private final int fromCount;
    private final int amethystCost;
    private final ItemStack toItem;
    private final int toCount;
    private final int maxUses;
    private final float priceMultiplier;

    public ItemsAndAmethystsToItems(ItemLike fromItem, int fromCount, Item toItem, int toCount, int maxUses) {
        this(fromItem, fromCount, 1, toItem, toCount, maxUses);
    }

    public ItemsAndAmethystsToItems(ItemLike fromItem, int fromCount, int cost, Item toItem, int toCount, int maxUses) {
        this.fromItem = new ItemStack(fromItem);
        this.fromCount = fromCount;
        this.amethystCost = cost;
        this.toItem = new ItemStack(toItem);
        this.toCount = toCount;
        this.maxUses = maxUses;
        this.priceMultiplier = 0.05F;
    }

    @Nullable
    public MerchantOffer getOffer(Entity entity, RandomSource rand) {
        return new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, this.amethystCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, 0, this.priceMultiplier);
    }
}
