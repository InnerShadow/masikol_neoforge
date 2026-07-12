package by.masikol.masikol.item;

import by.masikol.masikol.MasikolMod;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModSmithingTemplates {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasikolMod.MOD_ID);

    private static final ResourceLocation EMPTY_SLOT_BOW = ResourceLocation.withDefaultNamespace("item/bow");
    private static final ResourceLocation EMPTY_SLOT_CROSSBOW = ResourceLocation.withDefaultNamespace("item/crossbow_standby");
    private static final ResourceLocation EMPTY_SLOT_INGOT = ResourceLocation.withDefaultNamespace("item/empty_slot_ingot");

    public static final DeferredItem<SmithingTemplateItem> MithrilUpgrade = ITEMS.register("mithril_upgrade",
            () -> new SmithingTemplateItem(
                    Component.translatable("item.bymasikolmod.smithing_template.mithril_upgrade.applies_to").withStyle(ChatFormatting.BLUE),
                    Component.translatable("item.bymasikolmod.smithing_template.mithril_upgrade.ingredients").withStyle(ChatFormatting.BLUE),
                    Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.fromNamespaceAndPath(MasikolMod.MOD_ID, "mithril_upgrade")))
                            .withStyle(ChatFormatting.GRAY),
                    Component.translatable("item.bymasikolmod.smithing_template.mithril_upgrade.base_slot_description"),
                    Component.translatable("item.bymasikolmod.smithing_template.mithril_upgrade.additions_slot_description"),
                    List.of(EMPTY_SLOT_BOW, EMPTY_SLOT_CROSSBOW),
                    List.of(EMPTY_SLOT_INGOT)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
