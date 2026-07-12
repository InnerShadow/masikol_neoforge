package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModMaterialItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    public static final DeferredItem<Item> RawMithril = ITEMS.register("raw_mithril",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MithrilIngot = ITEMS.register("mithril_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> Peat = ITEMS.register("peat",
            () -> new Item(new Item.Properties()
                    .component(DataComponents.LORE, new ItemLore(List.of(
                            Component.translatable("item.bymasikolmod.peat.tooltip")
                    )))
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
