package by.masikol.masikol.event;

import by.masikol.masikol.progression.BossProgressionData;
import by.masikol.masikol.progression.ProgressionTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

// Detects kills of the 3 optional bosses and raises the matching flag in BossProgressionData,
// which ProgressionMobEquipment then reads to decide zombie/skeleton gear and spawn rate.
public class BossProgressionEvents {

    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getEntity().level() instanceof ServerLevel level)) {
            return;
        }

        BossProgressionData data = BossProgressionData.get(level);
        ProgressionTier tierBefore = data.getTier();
        boolean mithrilBefore = data.isMithrilUnlocked();

        if (event.getEntity() instanceof WitherBoss) {
            data.setWitherKilled(true);
        } else if (event.getEntity() instanceof Warden) {
            data.setWardenKilled(true);
        } else if (event.getEntity() instanceof EnderDragon) {
            data.setDragonKilled(true);
        } else {
            return;
        }

        ProgressionTier tierAfter = data.getTier();
        if (tierAfter == ProgressionTier.TIER_1 && tierBefore == ProgressionTier.NONE) {
            announce(level, "progression.bymasikolmod.tier1");
        } else if (tierAfter == ProgressionTier.TIER_2 && tierBefore != ProgressionTier.TIER_2) {
            announce(level, "progression.bymasikolmod.tier2");
        }
        if (data.isMithrilUnlocked() && !mithrilBefore) {
            announce(level, "progression.bymasikolmod.tier3");
        }
    }

    private static void announce(ServerLevel level, String translationKey) {
        level.getServer().getPlayerList().broadcastSystemMessage(
                Component.translatable(translationKey).withStyle(ChatFormatting.GOLD), false
        );
    }
}
