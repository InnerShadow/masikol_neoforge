package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    public static final DeferredItem<Item> QuestBook = ITEMS.register("quest_book",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RawMithril = ITEMS.register("raw_mithril",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MithrilIngot = ITEMS.register("mithril_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ToxicRawMeat = ITEMS.register("toxic_raw_meat",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.3F)
                            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 0), 0.75F)
                            .build()
            )));

    public static final DeferredItem<Item> ToxicCookedMeat = ITEMS.register("toxic_cooked_meat",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(0.8F)
                            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 0), 0.05F)
                            .build()
            )));

    public static final DeferredItem<Item> Peat = ITEMS.register("peat",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
