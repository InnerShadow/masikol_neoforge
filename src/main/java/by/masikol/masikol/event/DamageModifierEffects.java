package by.masikol.masikol.event;

import by.masikol.masikol.inventory.AccessorySlots;
import by.masikol.masikol.inventory.ModAttachments;
import by.masikol.masikol.item.DamageModifierItem;
import by.masikol.masikol.item.GuaranteedDamageModifierItem;
import by.masikol.masikol.item.RandomDamageModifierItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

// Rolls each unlocked accessory slot independently - stacking multiple damage modifiers across
// slots is allowed on purpose, since these are plain accessory slots with no further restriction.
// Hooked on LivingIncomingDamageEvent (fires before armor/enchantment reduction) so the bonus behaves
// like ordinary weapon damage rather than bypassing armor the way MithrilArmorEffects's bonus does.
public class DamageModifierEffects {

    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player attacker)) {
            return;
        }

        AccessorySlots slots = attacker.getData(ModAttachments.ACCESSORY_SLOTS);
        RandomSource random = attacker.getRandom();

        float bonus = 0.0F;
        for (int i = 0; i < slots.getUnlockedSlots(); i++) {
            Item item = slots.getItem(i).getItem();
            if (item instanceof DamageModifierItem modifier && random.nextFloat() < modifier.getProcChance()) {
                bonus += modifier.getDamageBonus();
            } else if (item instanceof GuaranteedDamageModifierItem guaranteed) {
                bonus += guaranteed.getDamageBonus();
            } else if (item instanceof RandomDamageModifierItem randomModifier) {
                bonus += randomModifier.getMinBonus() + random.nextFloat() * (randomModifier.getMaxBonus() - randomModifier.getMinBonus());
            }
        }

        if (bonus > 0.0F) {
            event.setAmount(event.getAmount() + bonus);
        }
    }
}
