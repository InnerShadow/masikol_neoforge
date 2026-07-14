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

public class ModAccessoryItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    private static final float DAMAGE_BONUS = 1.5F;

    private static final int COMMON_COLOR = 0x3D7FFF;
    private static final int RARE_COLOR = 0xFF8C1A;
    private static final int EPIC_COLOR = 0xA335EE;
    private static final int LEGENDARY_COLOR = 0xFFB000;

    public static final DeferredItem<DamageModifierItem> CommonDamageModifier = ITEMS.register("common_damage_modifier",
            () -> new DamageModifierItem(itemProperties("common_damage_modifier", 0.25F), 0.25F, DAMAGE_BONUS, COMMON_COLOR));

    public static final DeferredItem<DamageModifierItem> RareDamageModifier = ITEMS.register("rare_damage_modifier",
            () -> new DamageModifierItem(itemProperties("rare_damage_modifier", 0.5F), 0.5F, DAMAGE_BONUS, RARE_COLOR));

    public static final DeferredItem<DamageModifierItem> EpicDamageModifier = ITEMS.register("epic_damage_modifier",
            () -> new DamageModifierItem(itemProperties("epic_damage_modifier", 0.75F), 0.75F, DAMAGE_BONUS, EPIC_COLOR));

    public static final DeferredItem<DamageModifierItem> LegendaryDamageModifier = ITEMS.register("legendary_damage_modifier",
            () -> new DamageModifierItem(itemProperties("legendary_damage_modifier", 1.0F), 1.0F, DAMAGE_BONUS, LEGENDARY_COLOR));

    public static final DeferredItem<EnderDragonGiftItem> EnderDragonGift = ITEMS.register("ender_dragon_gift",
            () -> new EnderDragonGiftItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(net.minecraft.world.item.Rarity.EPIC)
                    .component(DataComponents.LORE, new ItemLore(List.of(
                            Component.translatable("item.bymasikolmod.ender_dragon_gift.tooltip")
                    )))
            ));

    public static final DeferredItem<GuaranteedDamageModifierItem> CommonGuaranteedDamageModifier = ITEMS.register("common_guaranteed_damage_modifier",
            () -> new GuaranteedDamageModifierItem(guaranteedItemProperties("common_guaranteed_damage_modifier", 0.2F), 0.2F, COMMON_COLOR));

    public static final DeferredItem<GuaranteedDamageModifierItem> RareGuaranteedDamageModifier = ITEMS.register("rare_guaranteed_damage_modifier",
            () -> new GuaranteedDamageModifierItem(guaranteedItemProperties("rare_guaranteed_damage_modifier", 0.6F), 0.6F, RARE_COLOR));

    public static final DeferredItem<GuaranteedDamageModifierItem> EpicGuaranteedDamageModifier = ITEMS.register("epic_guaranteed_damage_modifier",
            () -> new GuaranteedDamageModifierItem(guaranteedItemProperties("epic_guaranteed_damage_modifier", 1.0F), 1.0F, EPIC_COLOR));

    public static final DeferredItem<GuaranteedDamageModifierItem> LegendaryGuaranteedDamageModifier = ITEMS.register("legendary_guaranteed_damage_modifier",
            () -> new GuaranteedDamageModifierItem(guaranteedItemProperties("legendary_guaranteed_damage_modifier", 1.5F), 1.5F, LEGENDARY_COLOR));

    public static final DeferredItem<RandomDamageModifierItem> CommonRandomDamageModifier = ITEMS.register("common_random_damage_modifier",
            () -> new RandomDamageModifierItem(randomItemProperties("common_random_damage_modifier", 0.0F, 0.6F), 0.0F, 0.6F, COMMON_COLOR));

    public static final DeferredItem<RandomDamageModifierItem> RareRandomDamageModifier = ITEMS.register("rare_random_damage_modifier",
            () -> new RandomDamageModifierItem(randomItemProperties("rare_random_damage_modifier", 0.4F, 1.0F), 0.4F, 1.0F, RARE_COLOR));

    public static final DeferredItem<RandomDamageModifierItem> EpicRandomDamageModifier = ITEMS.register("epic_random_damage_modifier",
            () -> new RandomDamageModifierItem(randomItemProperties("epic_random_damage_modifier", 0.8F, 1.2F), 0.8F, 1.2F, EPIC_COLOR));

    public static final DeferredItem<RandomDamageModifierItem> LegendaryRandomDamageModifier = ITEMS.register("legendary_random_damage_modifier",
            () -> new RandomDamageModifierItem(randomItemProperties("legendary_random_damage_modifier", 1.0F, 2.0F), 1.0F, 2.0F, LEGENDARY_COLOR));

    public static final DeferredItem<RandomAccessoryItem> RandomAccessory = ITEMS.register("random_accessory",
            () -> new RandomAccessoryItem(new Item.Properties()
                    .stacksTo(16)
                    .rarity(net.minecraft.world.item.Rarity.RARE)
                    .component(DataComponents.LORE, new ItemLore(List.of(
                            Component.translatable("item.bymasikolmod.random_accessory.tooltip")
                    )))
            ));

    private static Item.Properties itemProperties(String name, float procChance) {
        return new Item.Properties()
                .stacksTo(1)
                .component(DataComponents.LORE, new ItemLore(List.of(
                        Component.translatable("item.bymasikolmod." + name + ".tooltip", (int) (procChance * 100), DAMAGE_BONUS)
                )));
    }

    private static Item.Properties guaranteedItemProperties(String name, float damageBonus) {
        return new Item.Properties()
                .stacksTo(1)
                .component(DataComponents.LORE, new ItemLore(List.of(
                        Component.translatable("item.bymasikolmod." + name + ".tooltip", damageBonus)
                )));
    }

    private static Item.Properties randomItemProperties(String name, float minBonus, float maxBonus) {
        return new Item.Properties()
                .stacksTo(1)
                .component(DataComponents.LORE, new ItemLore(List.of(
                        Component.translatable("item.bymasikolmod." + name + ".tooltip", minBonus, maxBonus)
                )));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
