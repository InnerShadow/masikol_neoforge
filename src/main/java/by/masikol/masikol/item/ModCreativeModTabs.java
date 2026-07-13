package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import by.masikol.masikol.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MasikolMod.MOD_ID);

    public static final Supplier<CreativeModeTab> MASIKOL_ITEMS_TAB = CREATIVE_MOD_TAB.register(
            "masikol_items_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModMiscItems.QuestBook.get()))
                    .title(Component.translatable("creativetab.bymasikolmod.masikol_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModMiscItems.QuestBook.get());
                        output.accept(ModMaterialItems.RawMithril.get());
                        output.accept(ModMaterialItems.MithrilIngot.get());
                        output.accept(ModFoodItems.ToxicRawMeat.get());
                        output.accept(ModFoodItems.ToxicCookedMeat.get());
                        output.accept(ModMaterialItems.Peat.get());

                        output.accept(ModArmorItems.MithrilHelmet.get());
                        output.accept(ModArmorItems.MithrilChestplate.get());
                        output.accept(ModArmorItems.MithrilLeggings.get());
                        output.accept(ModArmorItems.MithrilBoots.get());

                        output.accept(ModToolItems.MithrilPickaxe.get());
                        output.accept(ModToolItems.MithrilAxe.get());
                        output.accept(ModToolItems.MithrilShovel.get());
                        output.accept(ModToolItems.MithrilHoe.get());
                        output.accept(ModToolItems.MithrilSword.get());

                        output.accept(ModSmithingTemplates.MithrilUpgrade.get());
                        output.accept(ModWeaponItems.MithrilBow.get());
                        output.accept(ModWeaponItems.MithrilCrossbow.get());

                        output.accept(ModAccessoryItems.CommonDamageModifier.get());
                        output.accept(ModAccessoryItems.RareDamageModifier.get());
                        output.accept(ModAccessoryItems.EpicDamageModifier.get());
                        output.accept(ModAccessoryItems.LegendaryDamageModifier.get());
                        output.accept(ModAccessoryItems.CommonGuaranteedDamageModifier.get());
                        output.accept(ModAccessoryItems.RareGuaranteedDamageModifier.get());
                        output.accept(ModAccessoryItems.EpicGuaranteedDamageModifier.get());
                        output.accept(ModAccessoryItems.LegendaryGuaranteedDamageModifier.get());
                        output.accept(ModAccessoryItems.CommonRandomDamageModifier.get());
                        output.accept(ModAccessoryItems.RareRandomDamageModifier.get());
                        output.accept(ModAccessoryItems.EpicRandomDamageModifier.get());
                        output.accept(ModAccessoryItems.LegendaryRandomDamageModifier.get());
                        output.accept(ModAccessoryItems.EnderDragonGift.get());

                        output.accept(ModBlocks.MITHRIL_BLOCK.get());
                        output.accept(ModBlocks.MITHRIL_ORE.get());
                        output.accept(ModBlocks.PEAT_BLOCK.get());
                        output.accept(ModBlocks.PEAT_ORE.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){
        CREATIVE_MOD_TAB.register(eventBus);
    }
}
