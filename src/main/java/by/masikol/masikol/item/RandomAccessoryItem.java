package by.masikol.masikol.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// Consumes itself and grants one accessory rolled from ModAccessoryPool. Adds to the player's
// inventory if there's room, otherwise drops it at their feet like any overflow pickup.
public class RandomAccessoryItem extends Item {

    public RandomAccessoryItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.consume(stack);
        }

        Item rolled = ModAccessoryPool.roll(level.getRandom());
        ItemStack reward = new ItemStack(rolled);
        // Grab the name before handing the stack to the inventory - Inventory#add mutates reward's
        // count down to 0 once it's placed, and ItemStack#getItem()/getHoverName() both report
        // count-0 stacks as Air, so reading the name afterward always showed "Air".
        Component rewardName = reward.getHoverName();

        // Must add the reward BEFORE shrinking the used stack. If this was the last one in hand,
        // shrinking first empties that exact slot, and Inventory#add happily reuses it for the
        // reward - only for ServerPlayerGameMode#useItem to then see our returned (now-empty) hand
        // stack and force that same slot back to ItemStack.EMPTY, wiping out the reward we just gave.
        if (!player.getInventory().add(reward)) {
            player.drop(reward, false);
        }
        stack.shrink(1);

        player.displayClientMessage(
                Component.translatable("item.bymasikolmod.random_accessory.used", rewardName)
                        .withStyle(ChatFormatting.AQUA),
                false
        );

        return InteractionResultHolder.consume(stack);
    }
}
