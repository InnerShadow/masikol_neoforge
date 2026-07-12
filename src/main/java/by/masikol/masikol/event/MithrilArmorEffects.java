package by.masikol.masikol.event;

import by.masikol.masikol.item.ModArmorItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

// Vanilla's own armor formula hard-caps damage reduction at 20 armor points (~80%), a cap a full
// netherite set already reaches - stacking more points via the ARMOR attribute would do nothing.
// So this bonus is applied as a second, independent multiplier on top of that capped result instead.
public class MithrilArmorEffects {

    private static final float DAMAGE_REDUCTION_PER_PIECE = 0.025F;

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    // Pre fires after vanilla armor/enchantment reduction is already baked into the damage value,
    // which is exactly where this extra layer needs to hook in.
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        int mithrilPieces = 0;
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (isMithrilArmor(entity.getItemBySlot(slot).getItem())) {
                mithrilPieces++;
            }
        }

        if (mithrilPieces > 0) {
            float multiplier = 1.0F - mithrilPieces * DAMAGE_REDUCTION_PER_PIECE;
            event.setNewDamage(event.getNewDamage() * multiplier);
        }
    }

    public static boolean isMithrilArmor(Item item) {
        return item == ModArmorItems.MithrilHelmet.get()
                || item == ModArmorItems.MithrilChestplate.get()
                || item == ModArmorItems.MithrilLeggings.get()
                || item == ModArmorItems.MithrilBoots.get();
    }
}
