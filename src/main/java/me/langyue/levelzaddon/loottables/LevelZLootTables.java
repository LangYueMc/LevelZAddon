package me.langyue.levelzaddon.loottables;

import me.langyue.levelzaddon.LevelZAddon;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.levelz.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class LevelZLootTables {

    public static void init() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!LevelZAddon.CONFIG.allowedChests.contains(id.toString())) {
                return;
            }
            LevelZAddon.LOGGER.info("LevelZ loot table hook in {}", id);
            if (LevelZAddon.CONFIG.enableLootTable) {
                if (LevelZAddon.CONFIG.findRareCandyChance > 0) {
                    tableBuilder.pool(buildLootPool(ItemInit.RARE_CANDY, LevelZAddon.CONFIG.findRareCandyChance));
                }
                if (LevelZAddon.CONFIG.findStrangePotionChance > 0) {
                    tableBuilder.pool(buildLootPool(ItemInit.STRANGE_POTION, LevelZAddon.CONFIG.findStrangePotionChance));
                }
            }
        }));
    }

    private static LootPool buildLootPool(Item item, Float chance) {
        return LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .conditionally(RandomChanceLootCondition.builder(chance))
                .bonusRolls(ConstantLootNumberProvider.create(LevelZAddon.CONFIG.bonusRollsWithLuck))
                .with(ItemEntry.builder(item))
                .build();
    }
}
