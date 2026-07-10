package by.masikol.masikol.event;

import by.masikol.masikol.MasikolMod;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModToolRules {

    public static final TagKey<Block> NEEDS_NETHERITE_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(MasikolMod.MOD_ID, "needs_netherite_tool")
    );

    public static void onModifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        HolderSet<Block> gatedBlocks = BuiltInRegistries.BLOCK.getOrCreateTag(NEEDS_NETHERITE_TOOL);

        event.getAllItems()
                .filter(item -> item instanceof PickaxeItem pickaxe && pickaxe.getTier() != Tiers.NETHERITE)
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
}
