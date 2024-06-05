package com.yungnickyoung.minecraft.ribbits.entity.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;

public class EnchantedItemForAmethyst implements ItemListing {
    private final ItemStack itemStack;
    private final int amethystCostMin;
    private final int amethystCostMax;
    private final int maxUses;
    private final float priceMultiplier;

    public EnchantedItemForAmethyst(Item item, int amethystCostMin, int amethystCostMax, int maxUses) {
        this(item, amethystCostMin, amethystCostMax, maxUses, 0.05F);
    }

    public EnchantedItemForAmethyst(Item item, int costMin, int costMax, int maxUses, float priceMultiplier) {
        this.itemStack = new ItemStack(item);
        this.amethystCostMin = costMin;
        this.amethystCostMax = costMax;
        this.maxUses = maxUses;
        this.priceMultiplier = priceMultiplier;
    }

    public MerchantOffer getOffer(Entity entity, RandomSource rand) {
        int randomEnchantCost = 5 + rand.nextInt(15);

        ItemStack outputItem = EnchantmentHelper.enchantItem(rand, new ItemStack(this.itemStack.getItem()), randomEnchantCost, false);
        int cost = rand.nextIntBetweenInclusive(this.amethystCostMin, this.amethystCostMax);

        ItemStack amethyst = new ItemStack(Items.AMETHYST_SHARD, cost);
        return new MerchantOffer(amethyst, outputItem, this.maxUses, 0, this.priceMultiplier);
    }
}
