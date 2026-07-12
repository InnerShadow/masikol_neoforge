package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The attackDamage/attackSpeed params passed to each createAttributes() call below are copied
// straight from vanilla's own netherite tools - they're per-tool-type balance, not per-material.
// The actual upgrade over netherite comes entirely from ModItemTiers.MITHRIL itself.
public class ModToolItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    public static final DeferredItem<PickaxeItem> MithrilPickaxe = ITEMS.register("mithril_pickaxe",
            () -> new PickaxeItem(
                    ModItemTiers.MITHRIL,
                    new Item.Properties().fireResistant().attributes(PickaxeItem.createAttributes(ModItemTiers.MITHRIL, 1.0F, -2.8F))
            ));

    public static final DeferredItem<AxeItem> MithrilAxe = ITEMS.register("mithril_axe",
            () -> new AxeItem(
                    ModItemTiers.MITHRIL,
                    new Item.Properties().fireResistant().attributes(AxeItem.createAttributes(ModItemTiers.MITHRIL, 5.0F, -3.0F))
            ));

    public static final DeferredItem<ShovelItem> MithrilShovel = ITEMS.register("mithril_shovel",
            () -> new ShovelItem(
                    ModItemTiers.MITHRIL,
                    new Item.Properties().fireResistant().attributes(ShovelItem.createAttributes(ModItemTiers.MITHRIL, 1.5F, -3.0F))
            ));

    public static final DeferredItem<HoeItem> MithrilHoe = ITEMS.register("mithril_hoe",
            () -> new HoeItem(
                    ModItemTiers.MITHRIL,
                    new Item.Properties().fireResistant().attributes(HoeItem.createAttributes(ModItemTiers.MITHRIL, -4.0F, 0.0F))
            ));

    public static final DeferredItem<SwordItem> MithrilSword = ITEMS.register("mithril_sword",
            () -> new SwordItem(
                    ModItemTiers.MITHRIL,
                    new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModItemTiers.MITHRIL, 3.0F, -2.4F))
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
