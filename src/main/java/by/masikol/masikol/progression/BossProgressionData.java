package by.masikol.masikol.progression;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.util.INBTSerializable;

// Tracks which of the 3 optional bosses (Warden, Wither, Ender Dragon) have ever been killed on
// this world. Attached to the Overworld specifically (not per-player, not per-dimension) since the
// gear/spawn-rate progression it drives (see ProgressionMobEquipment) is meant to be one shared
// world state - killing a boss in the Nether or End should still count.
public class BossProgressionData implements INBTSerializable<CompoundTag> {
    private boolean wardenKilled;
    private boolean witherKilled;
    private boolean dragonKilled;

    public static BossProgressionData get(ServerLevel level) {
        ServerLevel overworld = level.getServer().overworld();
        return overworld.getData(ModProgressionAttachments.BOSS_PROGRESSION);
    }

    public boolean isWardenKilled() {
        return wardenKilled;
    }

    public void setWardenKilled(boolean wardenKilled) {
        this.wardenKilled = wardenKilled;
    }

    public boolean isWitherKilled() {
        return witherKilled;
    }

    public void setWitherKilled(boolean witherKilled) {
        this.witherKilled = witherKilled;
    }

    public boolean isDragonKilled() {
        return dragonKilled;
    }

    public void setDragonKilled(boolean dragonKilled) {
        this.dragonKilled = dragonKilled;
    }

    // Dragon kill doesn't add its own tier rung - it just unlocks a small chance for TIER_2 gear to
    // roll as mithril instead of iron, so it's exposed as a flag rather than folded into getTier().
    public boolean isMithrilUnlocked() {
        return dragonKilled;
    }

    public ProgressionTier getTier() {
        int bossesDown = (wardenKilled ? 1 : 0) + (witherKilled ? 1 : 0);
        if (bossesDown >= 2) {
            return ProgressionTier.TIER_2;
        }
        return bossesDown == 1 ? ProgressionTier.TIER_1 : ProgressionTier.NONE;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("WardenKilled", wardenKilled);
        tag.putBoolean("WitherKilled", witherKilled);
        tag.putBoolean("DragonKilled", dragonKilled);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        wardenKilled = tag.getBoolean("WardenKilled");
        witherKilled = tag.getBoolean("WitherKilled");
        dragonKilled = tag.getBoolean("DragonKilled");
    }
}
