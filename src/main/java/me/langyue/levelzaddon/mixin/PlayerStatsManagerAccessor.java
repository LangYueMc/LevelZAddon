package me.langyue.levelzaddon.mixin;

import net.levelz.stats.PlayerStatsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerStatsManager.class)
public interface PlayerStatsManagerAccessor {

    @Accessor("levelProgress")
    void setLevelProgress(float levelProgress);

    @Accessor
    int getOverallLevel();
}
