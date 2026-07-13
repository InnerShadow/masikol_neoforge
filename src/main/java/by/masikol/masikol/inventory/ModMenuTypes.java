package by.masikol.masikol.inventory;

import by.masikol.masikol.MasikolMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, MasikolMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AccessoryMenu>> ACCESSORY_MENU = MENU_TYPES.register(
            "accessory_menu",
            () -> IMenuTypeExtension.create(AccessoryMenu::new)
    );

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
