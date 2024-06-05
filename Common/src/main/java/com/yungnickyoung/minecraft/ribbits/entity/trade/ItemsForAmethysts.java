package com.yungnickyoung.minecraft.ribbits.entity.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

public class ItemsForAmethysts implements ItemListing {
    private final ItemStack itemStack;
    private final int amethystCostMin;
    private final int amethystCostMax;
    private final int amountMin;
    private final int amountMax;
    private final int maxUses;
    private final float priceMultiplier;

    public ItemsForAmethysts(Item item, int costMin, int costMax, int amountMin, int amountMax, int maxUses) {
        this(new ItemStack(item), costMin, costMax, amountMin, amountMax, maxUses, 0.05f);
    }

    public ItemsForAmethysts(ItemStack item, int costMin, int costMax, int amountMin, int amountMax, int maxUses, float priceMultiplier) {
        this.itemStack = item;
        this.amethystCostMin = costMin;
        this.amethystCostMax = costMax;
        this.amountMin = amountMin;
        this.amountMax = amountMax;
        this.maxUses = maxUses;
        this.priceMultiplier = priceMultiplier;
    }

    public MerchantOffer getOffer(Entity entity, RandomSource rand) {
        return new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, rand.nextIntBetweenInclusive(this.amethystCostMin, this.amethystCostMax)),
                new ItemStack(this.itemStack.getItem(), rand.nextIntBetweenInclusive(this.amountMin, this.amountMax)), this.maxUses, 0, this.priceMultiplier);
    }
}
