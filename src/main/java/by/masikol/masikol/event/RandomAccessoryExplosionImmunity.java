package by.masikol.masikol.event;

import by.masikol.masikol.item.RandomAccessoryItem;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;

// Mirrors the hardcoded vanilla case that lets a dropped Nether Star survive an explosion
// (ItemEntity#hurt has a special-cased check for it) - there's no generic "fireResistant items
// survive explosions" rule in vanilla, fire resistance only blocks fire damage, so this needs its
// own explicit hook rather than an Item.Properties flag.
public class RandomAccessoryExplosionImmunity {

    public static void onInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
        if (event.isInvulnerable()) {
            return;
        }
        if (event.getEntity() instanceof ItemEntity itemEntity
                && itemEntity.getItem().getItem() instanceof RandomAccessoryItem
                && event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            event.setInvulnerable(true);
        }
    }
}
