package io.github.langyue.levelzaddon;

import io.github.langyue.levelzaddon.config.LevelZAddonConfig;
import io.github.langyue.levelzaddon.loottables.LevelZLootTables;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelZAddon implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("LevelZAddon");
    public static LevelZAddonConfig CONFIG;

    @Override
    public void onInitialize() {
        LevelZAddonConfig.init();
        LevelZLootTables.init();
    }
}
