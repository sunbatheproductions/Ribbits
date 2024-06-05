package com.yungnickyoung.minecraft.ribbits.module;

import com.google.common.collect.Maps;
import com.yungnickyoung.minecraft.ribbits.data.RibbitProfession;
import com.yungnickyoung.minecraft.ribbits.entity.trade.AmethystForItems;
import com.yungnickyoung.minecraft.ribbits.entity.trade.EnchantedItemForAmethyst;
import com.yungnickyoung.minecraft.ribbits.entity.trade.ItemListing;
import com.yungnickyoung.minecraft.ribbits.entity.trade.ItemsForAmethysts;
import com.yungnickyoung.minecraft.ribbits.entity.trade.PotionForAmethyst;
import net.minecraft.Util;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

import java.util.Map;

public class RibbitTradeModule {
    public static final ItemsForAmethysts MARACA_TRADE = new ItemsForAmethysts(ItemModule.MARACA.get(), 6, 8, 1, 1, 4);

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
                new ItemsForAmethysts(Items.MANGROVE_PROPAGULE, 2, 6, 4, 8, 16),
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
}
