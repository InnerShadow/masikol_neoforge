package by.masikol.masikol.event;

import by.masikol.masikol.item.ModArmorItems;
import by.masikol.masikol.item.ModToolItems;
import by.masikol.masikol.item.ModWeaponItems;
import by.masikol.masikol.progression.BossProgressionData;
import by.masikol.masikol.progression.ProgressionTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

// Reads the world's BossProgressionData tier to gear up naturally-occurring zombies/skeletons and,
// on rarer occasions, spawn an extra one alongside a natural spawn.
//
// Gearing happens in EntityJoinLevelEvent rather than FinalizeSpawnEvent, even though the latter
// fires earlier and looks like the obvious hook: AbstractSkeleton#populateDefaultEquipmentSlots
// unconditionally overwrites MAINHAND with a plain bow, and that runs *after* FinalizeSpawnEvent as
// part of vanilla's own Mob#finalizeSpawn - so gear set there would just get discarded for
// skeletons. EntityJoinLevelEvent fires once vanilla's own equipping is fully done.
public class ProgressionMobEquipment {

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    private static final float TIER_1_ARMOR_PIECE_CHANCE = 0.25F;
    private static final float TIER_1_WEAPON_CHANCE = 0.40F;
    private static final float TIER_1_EXTRA_SPAWN_CHANCE = 0.08F;
    private static final float TIER_2_EXTRA_SPAWN_CHANCE = 0.15F;
    // Kept low and paired with setDropChance(slot, 0F) below - mithril is meant to stay something
    // you craft via the Dragon fight/smithing template, not something you can farm off zombies.
    private static final float MITHRIL_PIECE_CHANCE = 0.10F;

    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.loadedFromDisk() || !(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }
        boolean isZombie = mob.getType() == EntityType.ZOMBIE;
        if (!isZombie && mob.getType() != EntityType.SKELETON) {
            return;
        }

        BossProgressionData data = BossProgressionData.get(level);
        ProgressionTier tier = data.getTier();
        if (tier == ProgressionTier.NONE) {
            return;
        }

        equip(mob, isZombie, tier, data.isMithrilUnlocked(), level.getRandom());
    }

    // Restricted to true ambient spawns (not spawners/eggs/commands/reinforcements) so this only
    // makes the wilderness scarier, without also speeding up spawner-based grinders.
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        if (event.getSpawnType() != MobSpawnType.NATURAL) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        boolean isZombie = event.getEntity().getType() == EntityType.ZOMBIE;
        if (!isZombie && event.getEntity().getType() != EntityType.SKELETON) {
            return;
        }

        ProgressionTier tier = BossProgressionData.get(level).getTier();
        float extraSpawnChance = switch (tier) {
            case NONE -> 0.0F;
            case TIER_1 -> TIER_1_EXTRA_SPAWN_CHANCE;
            case TIER_2 -> TIER_2_EXTRA_SPAWN_CHANCE;
        };
        if (extraSpawnChance <= 0.0F || level.getRandom().nextFloat() >= extraSpawnChance) {
            return;
        }

        BlockPos origin = event.getEntity().blockPosition();
        RandomSource random = level.getRandom();
        BlockPos companionPos = origin.offset(random.nextInt(7) - 3, 0, random.nextInt(7) - 3);

        // EntityType#spawn calls Mob#finalizeSpawn directly (not through the NeoForge event bus),
        // so the companion can't recursively re-trigger this same handler.
        if (isZombie) {
            EntityType.ZOMBIE.spawn(level, null, companionPos, MobSpawnType.NATURAL, true, false);
        } else {
            EntityType.SKELETON.spawn(level, null, companionPos, MobSpawnType.NATURAL, true, false);
        }
    }

    private static void equip(Mob mob, boolean isZombie, ProgressionTier tier, boolean mithrilUnlocked, RandomSource random) {
        if (tier == ProgressionTier.TIER_1) {
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                if (random.nextFloat() < TIER_1_ARMOR_PIECE_CHANCE) {
                    mob.setItemSlot(slot, new ItemStack(Mob.getEquipmentForSlot(slot, rollTier1Material(random))));
                }
            }
            if (random.nextFloat() < TIER_1_WEAPON_CHANCE) {
                if (isZombie) {
                    mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(random.nextBoolean() ? Items.STONE_SWORD : Items.IRON_SWORD));
                } else {
                    equipEnchantedBow(mob, 1);
                }
            }
            return;
        }

        // TIER_2: guaranteed full default (iron) armor, with a small per-piece chance to roll
        // mithril instead once the Ender Dragon is dead.
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (mithrilUnlocked && random.nextFloat() < MITHRIL_PIECE_CHANCE) {
                mob.setItemSlot(slot, new ItemStack(mithrilArmorItem(slot)));
                mob.setDropChance(slot, 0.0F);
            } else {
                mob.setItemSlot(slot, new ItemStack(Mob.getEquipmentForSlot(slot, 3)));
            }
        }

        if (isZombie) {
            if (mithrilUnlocked && random.nextFloat() < MITHRIL_PIECE_CHANCE) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModToolItems.MithrilSword.get()));
                mob.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
            } else {
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            }
        } else if (mithrilUnlocked && random.nextFloat() < MITHRIL_PIECE_CHANCE) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MithrilBow.get()));
            mob.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        } else {
            equipEnchantedBow(mob, 2);
        }
    }

    // 45% leather / 30% golden / 25% chainmail - matches Mob#getEquipmentForSlot's chance indices.
    private static int rollTier1Material(RandomSource random) {
        float roll = random.nextFloat();
        if (roll < 0.45F) {
            return 0;
        }
        return roll < 0.75F ? 1 : 2;
    }

    private static Item mithrilArmorItem(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> ModArmorItems.MithrilHelmet.get();
            case CHEST -> ModArmorItems.MithrilChestplate.get();
            case LEGS -> ModArmorItems.MithrilLeggings.get();
            case FEET -> ModArmorItems.MithrilBoots.get();
            default -> throw new IllegalStateException("Unexpected armor slot: " + slot);
        };
    }

    private static void equipEnchantedBow(Mob mob, int powerLevel) {
        Holder<Enchantment> power = mob.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER);
        ItemStack bow = new ItemStack(Items.BOW);
        bow.enchant(power, powerLevel);
        mob.setItemSlot(EquipmentSlot.MAINHAND, bow);
    }
}
