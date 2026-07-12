package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModArmorItems {
    private static final int DURABILITY_FACTOR = 40;

    private static final ItemLore EXTRA_DEFENSE_LORE = new ItemLore(List.of(
            Component.translatable("item.bymasikolmod.mithril_armor.tooltip")
    ));

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    public static final DeferredItem<ArmorItem> MithrilHelmet = ITEMS.register("mithril_helmet",
            () -> new ArmorItem(
                    ModArmorMaterials.MITHRIL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .durability(ArmorItem.Type.HELMET.getDurability(DURABILITY_FACTOR))
                            .component(DataComponents.LORE, EXTRA_DEFENSE_LORE)
            ));

    public static final DeferredItem<ArmorItem> MithrilChestplate = ITEMS.register("mithril_chestplate",
            () -> new ArmorItem(
                    ModArmorMaterials.MITHRIL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(DURABILITY_FACTOR))
                            .component(DataComponents.LORE, EXTRA_DEFENSE_LORE)
            ));

    public static final DeferredItem<ArmorItem> MithrilLeggings = ITEMS.register("mithril_leggings",
            () -> new ArmorItem(
                    ModArmorMaterials.MITHRIL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(DURABILITY_FACTOR))
                            .component(DataComponents.LORE, EXTRA_DEFENSE_LORE)
            ));

    public static final DeferredItem<ArmorItem> MithrilBoots = ITEMS.register("mithril_boots",
            () -> new ArmorItem(
                    ModArmorMaterials.MITHRIL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.BOOTS.getDurability(DURABILITY_FACTOR))
                            .component(DataComponents.LORE, EXTRA_DEFENSE_LORE)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
