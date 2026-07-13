package by.masikol.masikol.item;

import by.masikol.masikol.inventory.AccessorySlots;
import by.masikol.masikol.inventory.ModAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// One-time use: permanently raises the player's unlocked accessory slot count from 1 to 2, then
// consumes itself. Safe to hold multiple copies or use it again after slot 2 is already unlocked -
// it just refuses and tells the player instead of unlocking a slot that doesn't exist.
public class EnderDragonGiftItem extends Item {
    private static final int GRANTED_SLOTS = 2;

    public EnderDragonGiftItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.consume(stack);
        }

        AccessorySlots slots = player.getData(ModAttachments.ACCESSORY_SLOTS);
        if (slots.getUnlockedSlots() >= GRANTED_SLOTS) {
            player.displayClientMessage(
                    Component.translatable("item.bymasikolmod.ender_dragon_gift.already_used").withStyle(ChatFormatting.RED),
                    false
            );
            return InteractionResultHolder.fail(stack);
        }

        slots.setUnlockedSlots(GRANTED_SLOTS);
        player.syncData(ModAttachments.ACCESSORY_SLOTS);
        stack.shrink(1);

        player.displayClientMessage(
                Component.translatable("item.bymasikolmod.ender_dragon_gift.used").withStyle(ChatFormatting.LIGHT_PURPLE),
                false
        );
        return InteractionResultHolder.consume(stack);
    }
}
