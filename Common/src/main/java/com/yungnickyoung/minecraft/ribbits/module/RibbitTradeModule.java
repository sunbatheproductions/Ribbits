package com.yungnickyoung.minecraft.ribbits.module;

import com.google.common.collect.Maps;
import com.yungnickyoung.minecraft.ribbits.data.RibbitProfession;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.Map;

public class RibbitTradeModule {

    public static final Map<RibbitProfession, ItemListing[]> TRADES = Util.make(Maps.newHashMap(), (tradeMap) -> {
        tradeMap.put(RibbitProfessionModule.MERCHANT, new ItemListing[]{new AmethystForItems(Items.WHEAT, 20, 16), new AmethystForItems(Items.POTATO, 26, 16), new AmethystForItems(Items.CARROT, 22, 16), new AmethystForItems(Items.BEETROOT, 15, 16), new ItemsForAmethysts(Items.BREAD, 1, 6, 16)});
        tradeMap.put(RibbitProfessionModule.FISHERMAN, new ItemListing[]{new AmethystForItems(Items.STRING, 20, 16), new AmethystForItems(Items.COAL, 10, 16), new ItemsAndAmethystsToItems(Items.COD, 6, Items.COOKED_COD, 6, 16), new ItemsForAmethysts(Items.COD_BUCKET, 3, 1, 16)});
    });

    public interface ItemListing {
        @Nullable
        MerchantOffer getOffer(Entity var1, RandomSource var2);
    }

    static class AmethystForItems implements ItemListing {
        private final Item item;
        private final int cost;
        private final int maxUses;
        private final float priceMultiplier;

        public AmethystForItems(ItemLike item, int cost, int maxUses) {
            this.item = item.asItem();
            this.cost = cost;
            this.maxUses = maxUses;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource rand) {
            ItemStack offeredStack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(offeredStack, new ItemStack(Items.AMETHYST_SHARD), this.maxUses, 0, this.priceMultiplier);
        }
    }

    static class ItemsForAmethysts implements ItemListing {
        private final ItemStack itemStack;
        private final int amethystCost;
        private final int numberOfItems;
        private final int maxUses;
        private final float priceMultiplier;

        public ItemsForAmethysts(Block block, int cost, int amount, int maxUses) {
            this(new ItemStack(block), cost, amount, maxUses);
        }

        public ItemsForAmethysts(Item item, int cost, int amount) {
            this(new ItemStack(item), cost, amount, 12);
        }

        public ItemsForAmethysts(Item item, int cost, int amount, int maxUses) {
            this(new ItemStack(item), cost, amount, maxUses);
        }

        public ItemsForAmethysts(ItemStack item, int cost, int amount, int maxUses) {
            this(item, cost, amount, maxUses, 0.05F);
        }

        public ItemsForAmethysts(ItemStack item, int cost, int amount, int maxUses, float priceMultiplier) {
            this.itemStack = item;
            this.amethystCost = cost;
            this.numberOfItems = amount;
            this.maxUses = maxUses;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource rand) {
            return new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, this.amethystCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, 0, this.priceMultiplier);
        }
    }

    static class ItemsAndAmethystsToItems implements ItemListing {
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
}
