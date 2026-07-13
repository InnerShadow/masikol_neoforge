package by.masikol.masikol.network;

import by.masikol.masikol.inventory.AccessoryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetworking {

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                OpenAccessoryMenuPayload.TYPE,
                OpenAccessoryMenuPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> {
                    if (context.player() instanceof ServerPlayer serverPlayer) {
                        serverPlayer.openMenu(new SimpleMenuProvider(
                                (windowId, inventory, player) -> new AccessoryMenu(windowId, inventory),
                                Component.translatable("gui.bymasikolmod.accessory_menu.title")
                        ));
                    }
                })
        );
    }
}
