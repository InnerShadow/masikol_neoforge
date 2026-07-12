package by.masikol.masikol.event;

import by.masikol.masikol.MasikolMod;
import by.masikol.masikol.item.ModItemTiers;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Vanilla's tool-tier gate only goes up to "needs diamond" - there's no tag for requiring
// better-than-netherite tools. So blocks in NEEDS_NETHERITE_TOOL are gated here in code instead:
// every pickaxe below mithril tier gets a rule prepended that forces them to mine those blocks
// slowly (speed 1.0, like an unsuited tool) and without drops.
public class ModToolRules {

    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(MasikolMod.MOD_ID, "needs_netherite_tool")
    );

    public static void onModifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        HolderSet<Block> gatedBlocks = BuiltInRegistries.BLOCK.getOrCreateTag(NEEDS_NETHERITE_TOOL);

        event.getAllItems()
                .filter(item -> item instanceof PickaxeItem pickaxe && !isSufficientTier(pickaxe.getTier()))
                .forEach(item -> event.modify(item, builder -> {
                    Tool currentTool = item.components().get(DataComponents.TOOL);
                    if (currentTool == null) {
                        return;
                    }

                    List<Tool.Rule> rules = new ArrayList<>();
                    rules.add(new Tool.Rule(gatedBlocks, Optional.of(1.0F), Optional.of(false)));
                    rules.addAll(currentTool.rules());

                    builder.set(DataComponents.TOOL, new Tool(rules, currentTool.defaultMiningSpeed(), currentTool.damagePerBlock()));
                }));
    }

    // Mithril isn't Tiers.NETHERITE, so without this it would wrongly get the "too weak" rule above
    // applied to itself too - making mithril ore unminable with a mithril pickaxe.
    private static boolean isSufficientTier(Tier tier) {
        return tier == Tiers.NETHERITE || tier == ModItemTiers.MITHRIL;
    }
}
