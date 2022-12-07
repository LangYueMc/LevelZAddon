package me.langyue.levelzaddon.mixin;

import me.langyue.levelzaddon.LevelZAddon;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.CriteriaInit;
import net.levelz.item.RareCandyItem;
import net.levelz.network.PlayerStatsServerPacket;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RareCandyItem.class)
public abstract class RareCandyItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!LevelZAddon.CONFIG.rareCandyIgnoreMaxLevel) {
            return;
        }
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            if (!user.isCreative()) {
                stack.decrement(1);
            }
            levelUp((ServerPlayerEntity) user);
            cir.cancel();
            cir.setReturnValue(TypedActionResult.success(stack, world.isClient()));
        }
    }

//    @Redirect(method = "use", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/levelz/access/PlayerSyncAccess;addLevelExperience(I)V"))
//    private void onUse(PlayerSyncAccess instance, int i) {
//        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) instance;
//        levelUp(serverPlayer);
//    }

    private void levelUp(ServerPlayerEntity user) {
        try {
            PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess) user).getPlayerStatsManager();
            playerStatsManager.addExperienceLevels(1);
            PlayerStatsManagerAccessor playerStatsManagerAccessor = (PlayerStatsManagerAccessor) playerStatsManager;
            playerStatsManagerAccessor.setLevelProgress(0);
            PlayerStatsServerPacket.writeS2CSkillPacket(playerStatsManager, user);
            PlayerStatsManager.onLevelUp(user, playerStatsManagerAccessor.getOverallLevel());
            CriteriaInit.LEVEL_UP.trigger(user, playerStatsManagerAccessor.getOverallLevel());
            user.server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, user));
            user.getScoreboard().forEachScore(CriteriaInit.LEVELZ, user.getEntityName(), ScoreboardPlayerScore::incrementScore);
            if (playerStatsManagerAccessor.getOverallLevel() > 0) {
                user.world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, user.getSoundCategory(), 1.0F, 1.0F);
            }
        } catch (Throwable e) {
            LevelZAddon.LOGGER.warn("There is a problem with using rare candy", e);
        }
    }
}
