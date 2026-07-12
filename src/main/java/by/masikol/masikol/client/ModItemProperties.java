package by.masikol.masikol.client;

import by.masikol.masikol.item.ModWeaponItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

// Vanilla wires up "pulling"/"pull"/"charged"/"firework" per exact Item instance
// (ItemProperties.register(Items.BOW, ...) etc.), not for BowItem/CrossbowItem as a type - so our
// own bow/crossbow items don't get draw/charge animations for free and need the same predicates
// registered again here, pointed at our items instead.
public class ModItemProperties {

    public static void register() {
        ItemProperties.register(
                ModWeaponItems.MithrilBow.get(),
                ResourceLocation.withDefaultNamespace("pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    }
                    return entity.getUseItem() != stack
                            ? 0.0F
                            : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
                }
        );
        ItemProperties.register(
                ModWeaponItems.MithrilBow.get(),
                ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );

        ItemProperties.register(
                ModWeaponItems.MithrilCrossbow.get(),
                ResourceLocation.withDefaultNamespace("pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    }
                    return CrossbowItem.isCharged(stack)
                            ? 0.0F
                            : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(stack, entity);
                }
        );
        ItemProperties.register(
                ModWeaponItems.MithrilCrossbow.get(),
                ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack)
                        ? 1.0F
                        : 0.0F
        );
        ItemProperties.register(
                ModWeaponItems.MithrilCrossbow.get(),
                ResourceLocation.withDefaultNamespace("charged"),
                (stack, level, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
        );
        ItemProperties.register(
                ModWeaponItems.MithrilCrossbow.get(),
                ResourceLocation.withDefaultNamespace("firework"),
                (stack, level, entity, seed) -> {
                    ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
                    return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
                }
        );
    }
}
