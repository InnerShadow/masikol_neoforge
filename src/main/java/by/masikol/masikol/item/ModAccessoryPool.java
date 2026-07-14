package by.masikol.masikol.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

// The weighted pool RandomAccessoryItem draws from - every placeable accessory (the three damage
// modifier flavors) except EnderDragonGiftItem, which doesn't implement IAccessoryItem and can't
// be placed in a slot at all. Rarity is rolled first with the tier weights below, then one of the
// three category items sharing that rarity is picked uniformly.
public class ModAccessoryPool {
    private ModAccessoryPool() {
    }

    // The requested 50 / 25 / 15.623 / 8.375 don't add up to 100 - corrected to the nearest clean
    // binary split (1/2, 1/4, 5/32, 3/32) that does: 50 / 25 / 15.625 / 9.375.
    private static final float COMMON_WEIGHT = 0.5F;
    private static final float RARE_WEIGHT = 0.25F;
    private static final float EPIC_WEIGHT = 0.15625F;

    private static final List<Supplier<? extends Item>> COMMON_POOL = List.of(
            ModAccessoryItems.CommonDamageModifier,
            ModAccessoryItems.CommonGuaranteedDamageModifier,
            ModAccessoryItems.CommonRandomDamageModifier
    );
    private static final List<Supplier<? extends Item>> RARE_POOL = List.of(
            ModAccessoryItems.RareDamageModifier,
            ModAccessoryItems.RareGuaranteedDamageModifier,
            ModAccessoryItems.RareRandomDamageModifier
    );
    private static final List<Supplier<? extends Item>> EPIC_POOL = List.of(
            ModAccessoryItems.EpicDamageModifier,
            ModAccessoryItems.EpicGuaranteedDamageModifier,
            ModAccessoryItems.EpicRandomDamageModifier
    );
    private static final List<Supplier<? extends Item>> LEGENDARY_POOL = List.of(
            ModAccessoryItems.LegendaryDamageModifier,
            ModAccessoryItems.LegendaryGuaranteedDamageModifier,
            ModAccessoryItems.LegendaryRandomDamageModifier
    );

    public static Item roll(RandomSource random) {
        float roll = random.nextFloat();
        List<Supplier<? extends Item>> pool;
        if (roll < COMMON_WEIGHT) {
            pool = COMMON_POOL;
        } else if (roll < COMMON_WEIGHT + RARE_WEIGHT) {
            pool = RARE_POOL;
        } else if (roll < COMMON_WEIGHT + RARE_WEIGHT + EPIC_WEIGHT) {
            pool = EPIC_POOL;
        } else {
            pool = LEGENDARY_POOL;
        }
        return pool.get(random.nextInt(pool.size())).get();
    }
}
