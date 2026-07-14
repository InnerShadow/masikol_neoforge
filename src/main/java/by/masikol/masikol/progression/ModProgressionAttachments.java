package by.masikol.masikol.progression;

import by.masikol.masikol.MasikolMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModProgressionAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MasikolMod.MOD_ID);

    // No .sync() - this only drives server-side mob equipping/spawning decisions, nothing client
    // ever needs to read directly.
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<BossProgressionData>> BOSS_PROGRESSION =
            ATTACHMENT_TYPES.register(
                    "boss_progression",
                    () -> AttachmentType.serializable(BossProgressionData::new).build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
