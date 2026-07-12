package by.masikol.masikol.client;

import by.masikol.masikol.event.MithrilArmorEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class MithrilArmorHud {

    private static final ResourceLocation ARMOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_full");
    private static final ResourceLocation ARMOR_HALF_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_half");
    private static final ResourceLocation ARMOR_EMPTY_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_empty");

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    /** Each equipped mithril piece is worth half an icon, so 4 pieces fill exactly 2 icons. */
    private static final int ICON_COUNT = ARMOR_SLOTS.length / 2;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        int halfUnits = 0;
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (MithrilArmorEffects.isMithrilArmor(player.getItemBySlot(slot).getItem())) {
                halfUnits++;
            }
        }
        if (halfUnits == 0) {
            return;
        }

        // Vanilla stacks HUD rows above the hotbar in fixed 10px steps, with the armor row's own
        // top edge landing at guiHeight() - 49 in the common case (10 hearts, no extra rows).
        // -59 continues that same pitch directly above it, with no gap.
        int x = guiGraphics.guiWidth() / 2 - 91;
        int y = guiGraphics.guiHeight() - 59;

        for (int i = 0; i < ICON_COUNT; i++) {
            ResourceLocation sprite;
            if (i * 2 + 1 < halfUnits) {
                sprite = ARMOR_FULL_SPRITE;
            } else if (i * 2 + 1 == halfUnits) {
                sprite = ARMOR_HALF_SPRITE;
            } else {
                sprite = ARMOR_EMPTY_SPRITE;
            }
            guiGraphics.blitSprite(sprite, x + i * 10, y, 9, 9);
        }
    }
}
