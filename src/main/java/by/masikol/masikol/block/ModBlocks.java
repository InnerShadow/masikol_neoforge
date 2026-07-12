package by.masikol.masikol.block;

import by.masikol.masikol.MasikolMod;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MasikolMod.MOD_ID);

    private static final DeferredRegister.Items BLOCK_ITEMS =
            DeferredRegister.createItems(MasikolMod.MOD_ID);

    // 1200.0F blast resistance matches obsidian - mithril is meant to survive explosions untouched.
    public static final DeferredBlock<Block> MITHRIL_ORE = registerBlock(
            "mithril_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 8),
                    BlockBehaviour.Properties.of()
                            .strength(4.0F, 1200.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DEEPSLATE)
            )
    );

    public static final DeferredBlock<Block> MITHRIL_BLOCK = registerBlock(
            "mithril_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .strength(4.0F, 1200.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)
            )
    );

    public static final DeferredBlock<Block> PEAT_ORE = registerBlock(
            "peat_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(0, 2),
                    BlockBehaviour.Properties.of()
                            .strength(3.0F, 3.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)
            )
    );

    public static final DeferredBlock<Block> PEAT_BLOCK = registerBlock(
            "peat_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .strength(5.0F, 6.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)
            )
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(
            String name,
            Supplier<T> blockSupplier
    ) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        registerBlockItem(name, block);
        return block;
    }

    private static <T extends Block> void registerBlockItem(
            String name,
            DeferredBlock<T> block
    ) {
        BLOCK_ITEMS.register(
                name,
                () -> new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }
}