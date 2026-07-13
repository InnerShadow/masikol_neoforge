package by.masikol.masikol.inventory;

import by.masikol.masikol.MasikolMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MasikolMod.MOD_ID);

    private static final StreamCodec<RegistryFriendlyByteBuf, AccessorySlots> STREAM_CODEC = StreamCodec.of(
            (buf, slots) -> {
                for (int i = 0; i < AccessorySlots.SLOT_COUNT; i++) {
                    ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, slots.getItem(i));
                }
                buf.writeVarInt(slots.getUnlockedSlots());
            },
            buf -> {
                AccessorySlots slots = new AccessorySlots();
                for (int i = 0; i < AccessorySlots.SLOT_COUNT; i++) {
                    slots.setItem(i, ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
                }
                slots.setUnlockedSlots(buf.readVarInt());
                return slots;
            }
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AccessorySlots>> ACCESSORY_SLOTS =
            ATTACHMENT_TYPES.register(
                    "accessory_slots",
                    () -> AttachmentType.serializable(AccessorySlots::new)
                            .sync(STREAM_CODEC)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
