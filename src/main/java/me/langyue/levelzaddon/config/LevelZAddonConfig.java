package me.langyue.levelzaddon.config;

import me.langyue.levelzaddon.LevelZAddon;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.Arrays;
import java.util.List;

@Config(name = "levelz_addon")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class LevelZAddonConfig implements ConfigData {

    @Comment("The rare candy can ignore max lvl")
    public boolean rareCandyIgnoreMaxLevel = true;

    @ConfigEntry.Gui.RequiresRestart
    public boolean enableLootTable = true;

    @ConfigEntry.Gui.RequiresRestart
    @Comment("Higher Number makes Luck give you potential for extra LevelZ loot.")
    public Float bonusRollsWithLuck = 1.2f;

    @ConfigEntry.Gui.RequiresRestart
    @Comment("Chance to get a rare candy from chests.")
    public Float findRareCandyChance = 0.1f;

    @ConfigEntry.Gui.RequiresRestart
    @Comment("Chance to get a strange potion from chests.")
    public Float findStrangePotionChance = 0f;

    @ConfigEntry.Gui.RequiresRestart
    @Comment("Must be the full loot table id.")
    public List<String> allowedChests = Arrays.asList(
            "minecraft:chests/village/village_temple",
            "minecraft:chests/village/village_snowy_house",
            "minecraft:chests/village/village_plains_house",
            "minecraft:chests/village/village_savanna_house",
            "minecraft:chests/village/village_taiga_house",
            "minecraft:chests/village/village_desert_house",
            "minecraft:chests/ancient_city",
            "minecraft:chests/ancient_city_ice_box",
            "minecraft:chests/simple_dungeon",
            "minecraft:chests/bastion_bridge",
            "minecraft:chests/bastion_treasure",
            "minecraft:chests/bastion_hoglin_stable",
            "minecraft:chests/bastion_other",
            "minecraft:chests/ruined_portal",
            "minecraft:chests/shipwreck_map",
            "minecraft:chests/shipwreck_supply",
            "minecraft:chests/shipwreck_treasure",
            "minecraft:chests/stronghold_crossing",
            "minecraft:chests/stronghold_corridor",
            "minecraft:chests/nether_bridge",
            "minecraft:chests/abandoned_mineshaft",
            "minecraft:chests/spawn_bonus_chest",
            "minecraft:chests/underwater_ruin_small",
            "minecraft:chests/underwater_ruin_big",
            "minecraft:chests/buried_treasure",
            "minecraft:chests/desert_pyramid",
            "minecraft:chests/igloo_chest",
            "minecraft:chests/pillager_outpost",
            "minecraft:chests/jungle_temple",
            "minecraft:chests/jungle_temple_dispenser",
            "minecraft:chests/end_city_treasure",
            "minecraft:chests/stronghold_library",
            "minecraft:chests/woodland_mansion",
            "minecraft:gameplay/cat_morning_gift"
    );

    public static void init() {
        AutoConfig.register(LevelZAddonConfig.class, JanksonConfigSerializer::new);
        LevelZAddon.CONFIG = AutoConfig.getConfigHolder(LevelZAddonConfig.class).getConfig();
    }
}
