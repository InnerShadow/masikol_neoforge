package by.masikol.masikol.progression;

// NONE: neither Warden nor Wither is dead yet - vanilla spawn behavior.
// TIER_1: exactly one of Warden/Wither is dead - zombies/skeletons occasionally get gear upgrades.
// TIER_2: both Warden and Wither are dead - zombies/skeletons are guaranteed a full default armor
// set and a better weapon. Mithril gear on top of TIER_2 is a separate flag (see
// BossProgressionData#isMithrilUnlocked), unlocked by the Ender Dragon instead of this tier ladder.
public enum ProgressionTier {
    NONE,
    TIER_1,
    TIER_2
}
