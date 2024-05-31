package com.yungnickyoung.minecraft.ribbits.module;

import com.google.common.collect.Maps;
import com.yungnickyoung.minecraft.ribbits.data.RibbitProfession;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class RibbitTradeModule {

    public static final Map<RibbitProfession, ItemListing[]> TRADES = Util.make(Maps.newHashMap(), (tradeMap) -> {
        tradeMap.put(RibbitProfessionModule.MERCHANT, new ItemListing[]{
                new ItemsForAmethysts(Items.SLIME_BALL, 8, 12, 2, 8, 16),
                new ItemsForAmethysts(Items.SADDLE, 12, 16, 1, 1, 16),
                new ItemsForAmethysts(Items.NAUTILUS_SHELL, 24, 28, 1, 1, 16),

                new ItemsForAmethysts(BlockModule.SWAMP_DAISY.get().asItem(), 1, 3, 4, 16, 16),
                new ItemsForAmethysts(BlockModule.BROWN_TOADSTOOL.get().asItem(), 1, 3, 4, 16, 16),
                new ItemsForAmethysts(BlockModule.RED_TOADSTOOL.get().asItem(), 1, 3, 4, 16, 16),
                new ItemsForAmethysts(BlockModule.TOADSTOOL.get().asItem(), 1, 3, 4, 16, 16),
                new ItemsForAmethysts(ItemModule.GIANT_LILYPAD.get(), 1, 3, 4, 16, 16),
                new ItemsForAmethysts(BlockModule.MOSSY_OAK_PLANKS.get().asItem(), 1, 3, 16, 32, 16),

                new ItemsForAmethysts(Items.MELON_SEEDS, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.PUMPKIN_SEEDS, 2, 6, 4, 8, 16),

                new PotionForAmethyst(Items.POTION, Potions.STRONG_LEAPING, 1, 2, 6, 8),
                new PotionForAmethyst(Items.SPLASH_POTION, null, 1, 2, 6, 8),

                new ItemsForAmethysts(Items.ACACIA_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.BIRCH_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.DARK_OAK_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.JUNGLE_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.OAK_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.SPRUCE_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.CHERRY_SAPLING, 2, 6, 4, 8, 16),
                new ItemsForAmethysts(Items.MANGROVE_PROPAGULE, 2, 6, 4, 8, 16)
        });
        tradeMap.put(RibbitProfessionModule.FISHERMAN, new ItemListing[]{
                new ItemsForAmethysts(Items.AXOLOTL_BUCKET, 4, 8, 1, 1, 16),
                new ItemsForAmethysts(Items.TROPICAL_FISH_BUCKET, 4, 8, 1, 1, 16),

                new ItemsForAmethysts(Items.COOKED_COD, 4, 8, 8, 24, 16),
                new ItemsForAmethysts(Items.COOKED_SALMON, 4, 8, 8, 24, 16),

                new EnchantedItemForAmethyst(Items.FISHING_ROD, 12, 16, 4),

                new AmethystForItems(Items.COD, 16, 32, 4, 8, 16),
                new AmethystForItems(Items.SALMON, 16, 32, 4, 8, 16)
        });
    });

    public interface ItemListing {
        @Nullable
        MerchantOffer getOffer(Entity var1, RandomSource var2);
    }

    static class AmethystForItems implements ItemListing {
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

    static class ItemsForAmethysts implements ItemListing {
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

    private static class PotionForAmethyst implements ItemListing {

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

    static class EnchantedItemForAmethyst implements ItemListing {
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
}
