package by.masikol.masikol.client;

import by.masikol.masikol.inventory.AccessorySlots;
import by.masikol.masikol.inventory.ModAttachments;
import by.masikol.masikol.item.GuaranteedDamageModifierItem;
import by.masikol.masikol.item.RandomDamageModifierItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

// Renders each equipped accessory as a small flat "medallion" hanging on the chest, using the same
// FIXED display context item frames use for their contents (a slightly-3D flat card look). Only
// unlocked slots are considered. Guaranteed and random damage modifiers hang on the right side of
// the chest (random below guaranteed); everything else (including chance-based damage modifiers)
// stays on the left, mirrored.
public class AccessoryRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final float SCALE = 0.2F;
    private static final float SPACING = 0.24F;
    private static final float LEFT_X_OFFSET = -0.15F;
    private static final float RIGHT_X_OFFSET = 0.15F;
    private static final float CHEST_Y_OFFSET = 0.28F;
    private static final float CHEST_Y_OFFSET_ROW2 = 0.28F + SPACING;
    private static final float CHEST_Z_OFFSET = -0.30F;

    private final ItemRenderer itemRenderer;

    public AccessoryRenderLayer(
            RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer,
            ItemRenderer itemRenderer
    ) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (player.isInvisible()) {
            return;
        }

        AccessorySlots slots = player.getData(ModAttachments.ACCESSORY_SLOTS);
        List<ItemStack> leftGroup = new ArrayList<>();
        List<ItemStack> rightTopGroup = new ArrayList<>();
        List<ItemStack> rightBottomGroup = new ArrayList<>();
        for (int i = 0; i < slots.getUnlockedSlots(); i++) {
            ItemStack stack = slots.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.getItem() instanceof RandomDamageModifierItem) {
                rightBottomGroup.add(stack);
            } else if (stack.getItem() instanceof GuaranteedDamageModifierItem) {
                rightTopGroup.add(stack);
            } else {
                leftGroup.add(stack);
            }
        }
        if (leftGroup.isEmpty() && rightTopGroup.isEmpty() && rightBottomGroup.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        getParentModel().body.translateAndRotate(poseStack);

        renderGroup(leftGroup, LEFT_X_OFFSET, CHEST_Y_OFFSET, poseStack, bufferSource, packedLight, player);
        renderGroup(rightTopGroup, RIGHT_X_OFFSET, CHEST_Y_OFFSET, poseStack, bufferSource, packedLight, player);
        renderGroup(rightBottomGroup, RIGHT_X_OFFSET, CHEST_Y_OFFSET_ROW2, poseStack, bufferSource, packedLight, player);

        poseStack.popPose();
    }

    private void renderGroup(
            List<ItemStack> group,
            float centerX,
            float centerY,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            AbstractClientPlayer player
    ) {
        if (group.isEmpty()) {
            return;
        }

        float startX = centerX - (group.size() - 1) * SPACING / 2.0F;
        for (int i = 0; i < group.size(); i++) {
            poseStack.pushPose();
            poseStack.translate(startX + i * SPACING, centerY, CHEST_Z_OFFSET);
            poseStack.scale(SCALE, SCALE, SCALE);
            itemRenderer.renderStatic(
                    group.get(i),
                    ItemDisplayContext.FIXED,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    bufferSource,
                    player.level(),
                    player.getId()
            );
            poseStack.popPose();
        }
    }
}
