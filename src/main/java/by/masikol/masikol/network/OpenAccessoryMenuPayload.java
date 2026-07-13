package by.masikol.masikol.network;

import by.masikol.masikol.MasikolMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenAccessoryMenuPayload() implements CustomPacketPayload {
    public static final Type<OpenAccessoryMenuPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MasikolMod.MOD_ID, "open_accessory_menu"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenAccessoryMenuPayload> STREAM_CODEC =
            StreamCodec.unit(new OpenAccessoryMenuPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
