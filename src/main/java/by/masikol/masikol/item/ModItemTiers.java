package by.masikol.masikol.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ModItemTiers {

    // Every stat here is set a notch above netherite's own (2031 uses, 9.0F speed, 4.0F damage,
    // 15 enchantment value) so mithril tools are a genuine upgrade path past the vanilla ceiling.
    public static final Tier MITHRIL = new ModTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2500,
            10.0F,
            4.5F,
            18,
            () -> Ingredient.of(ModMaterialItems.MithrilIngot.get())
    );
}
