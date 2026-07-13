package by.masikol.masikol.client;

import by.masikol.masikol.network.OpenAccessoryMenuPayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    private static final String CATEGORY = "key.categories.bymasikolmod";

    public static final KeyMapping OPEN_ACCESSORIES = new KeyMapping(
            "key.bymasikolmod.open_accessories",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            CATEGORY
    );

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_ACCESSORIES);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        while (OPEN_ACCESSORIES.consumeClick()) {
            PacketDistributor.sendToServer(new OpenAccessoryMenuPayload());
        }
    }
}
