package by.masikol.masikol;

import by.masikol.masikol.block.ModBlocks;
import by.masikol.masikol.event.DamageModifierEffects;
import by.masikol.masikol.event.MithrilArmorEffects;
import by.masikol.masikol.event.ModToolRules;
import by.masikol.masikol.inventory.ModAttachments;
import by.masikol.masikol.inventory.ModMenuTypes;
import by.masikol.masikol.item.ModAccessoryItems;
import by.masikol.masikol.item.ModArmorItems;
import by.masikol.masikol.item.ModArmorMaterials;
import by.masikol.masikol.item.ModCreativeModTabs;
import by.masikol.masikol.item.ModFoodItems;
import by.masikol.masikol.item.ModMaterialItems;
import by.masikol.masikol.item.ModMiscItems;
import by.masikol.masikol.item.ModSmithingTemplates;
import by.masikol.masikol.item.ModToolItems;
import by.masikol.masikol.item.ModWeaponItems;
import by.masikol.masikol.network.ModNetworking;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MasikolMod.MOD_ID)
public class MasikolMod{
    public static final String MOD_ID = "bymasikolmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MasikolMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModToolRules::onModifyDefaultComponents);
        modEventBus.addListener(ModNetworking::register);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(MithrilArmorEffects::onLivingDamagePre);
        NeoForge.EVENT_BUS.addListener(DamageModifierEffects::onIncomingDamage);

        ModCreativeModTabs.register(modEventBus);

        ModMiscItems.register(modEventBus);
        ModMaterialItems.register(modEventBus);
        ModFoodItems.register(modEventBus);
        ModArmorMaterials.register(modEventBus);
        ModArmorItems.register(modEventBus);
        ModToolItems.register(modEventBus);
        ModWeaponItems.register(modEventBus);
        ModSmithingTemplates.register(modEventBus);
        ModAccessoryItems.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
//            event.accept(ModMiscItems.QuestBook);
//        }
//
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
//            event.accept(ModBlocks.MITHRIL_ORE);
//            event.accept(ModBlocks.MITHRIL_BLOCK);
//        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
