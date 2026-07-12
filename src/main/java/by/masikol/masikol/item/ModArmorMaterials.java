package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, MasikolMod.MOD_ID);

    // Defense values match netherite exactly - a full set already saturates the vanilla armor bar
    // (20 points/10 icons is a hard visual and formula cap), so matching rather than exceeding it
    // avoids wasted stats. Toughness is bumped to 3.5F, but knockback resistance is left at 0 on
    // purpose so netherite keeps that one perk; mithril's real edge is the separate HUD bar in
    // MithrilArmorEffects, which isn't capped the way vanilla armor points are.
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MITHRIL = ARMOR_MATERIALS.register(
            "mithril",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.HELMET, 3);
                        map.put(ArmorItem.Type.CHESTPLATE, 8);
                        map.put(ArmorItem.Type.LEGGINGS, 6);
                        map.put(ArmorItem.Type.BOOTS, 3);
                        map.put(ArmorItem.Type.BODY, 11);
                    }),
                    10,
                    SoundEvents.ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.of(ModMaterialItems.MithrilIngot.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MasikolMod.MOD_ID, "mithril"))),
                    3.5F,
                    0.0F
            )
    );

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
