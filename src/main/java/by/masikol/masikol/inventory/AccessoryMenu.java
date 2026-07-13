package by.masikol.masikol.inventory;

import by.masikol.masikol.item.DamageModifierItem;
import by.masikol.masikol.item.GuaranteedDamageModifierItem;
import by.masikol.masikol.item.IAccessoryItem;
import by.masikol.masikol.item.RandomDamageModifierItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AccessoryMenu extends AbstractContainerMenu {
    private static final int ACCESSORY_SLOT_COUNT = AccessorySlots.SLOT_COUNT;
    private static final int SLOT_SIZE = 18;
    private static final int ACCESSORY_ROW_X = 43;
    private static final int ACCESSORY_ROW_Y = 20;

    private final AccessorySlots accessorySlots;

    public AccessoryMenu(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, playerInventory.player.getData(ModAttachments.ACCESSORY_SLOTS));
    }

    public AccessoryMenu(int windowId, Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        this(windowId, playerInventory, playerInventory.player.getData(ModAttachments.ACCESSORY_SLOTS));
    }

    private AccessoryMenu(int windowId, Inventory playerInventory, AccessorySlots accessorySlots) {
        super(ModMenuTypes.ACCESSORY_MENU.get(), windowId);
        this.accessorySlots = accessorySlots;

        for (int i = 0; i < ACCESSORY_SLOT_COUNT; i++) {
            this.addSlot(new AccessorySlot(accessorySlots, i, ACCESSORY_ROW_X + i * SLOT_SIZE, ACCESSORY_ROW_Y));
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * SLOT_SIZE, 51 + row * SLOT_SIZE));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * SLOT_SIZE, 109));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            result = stackInSlot.copy();

            if (index < ACCESSORY_SLOT_COUNT) {
                if (!this.moveItemStackTo(stackInSlot, ACCESSORY_SLOT_COUNT, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (stackInSlot.getItem() instanceof IAccessoryItem) {
                if (!this.moveItemStackTo(stackInSlot, 0, ACCESSORY_SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    // Locked slots (index >= unlockedSlots) reject placement and render as inactive, matching how
    // vanilla greys out armor slots that don't apply to the current entity.
    private static class AccessorySlot extends Slot {
        // Each damage-modifier flavor (chance-based, guaranteed, random-range) is its own
        // exclusivity group - at most one per group may be worn at a time, but one of each
        // together is fine.
        private static final Class<?>[] EXCLUSIVE_GROUPS = {
                DamageModifierItem.class,
                GuaranteedDamageModifierItem.class,
                RandomDamageModifierItem.class
        };

        private final int index;
        private final AccessorySlots accessorySlots;

        AccessorySlot(AccessorySlots accessorySlots, int index, int x, int y) {
            super(accessorySlots, index, x, y);
            this.accessorySlots = accessorySlots;
            this.index = index;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            if (!isUnlocked() || !(stack.getItem() instanceof IAccessoryItem)) {
                return false;
            }
            for (Class<?> group : EXCLUSIVE_GROUPS) {
                if (group.isInstance(stack.getItem()) && hasOtherOfType(group)) {
                    return false;
                }
            }
            return true;
        }

        private boolean hasOtherOfType(Class<?> type) {
            for (int i = 0; i < AccessorySlots.SLOT_COUNT; i++) {
                if (i != index && type.isInstance(accessorySlots.getItem(i).getItem())) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isActive() {
            return isUnlocked();
        }

        private boolean isUnlocked() {
            return index < accessorySlots.getUnlockedSlots();
        }
    }
}
