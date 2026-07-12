package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModWeaponItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    private static final ItemLore VELOCITY_LORE = new ItemLore(List.of(
            Component.translatable("item.bymasikolmod.mithril_ranged.tooltip")
    ));

    // Roughly +55% durability over vanilla (bow 384, crossbow 465) - the velocity/accuracy boost
    // itself lives in MithrilBowItem/MithrilCrossbowItem, not here.
    public static final DeferredItem<MithrilBowItem> MithrilBow = ITEMS.register("mithril_bow",
            () -> new MithrilBowItem(new Item.Properties()
                    .fireResistant()
                    .durability(600)
                    .component(DataComponents.LORE, VELOCITY_LORE)
            ));

    public static final DeferredItem<MithrilCrossbowItem> MithrilCrossbow = ITEMS.register("mithril_crossbow",
            () -> new MithrilCrossbowItem(new Item.Properties()
                    .fireResistant()
                    .stacksTo(1)
                    .durability(700)
                    .component(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY)
                    .component(DataComponents.LORE, VELOCITY_LORE)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
