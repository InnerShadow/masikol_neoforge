package by.masikol.masikol.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// shoot() is the override point shared with MithrilBowItem - see that class for why
// (CrossbowItem has no per-material tier either, and its power constants are private statics).
public class MithrilCrossbowItem extends CrossbowItem {

    private static final float VELOCITY_MULTIPLIER = 1.15F;
    private static final float INACCURACY_MULTIPLIER = 0.85F;

    public MithrilCrossbowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    protected void shoot(
            ServerLevel level,
            LivingEntity shooter,
            InteractionHand hand,
            ItemStack weapon,
            List<ItemStack> projectileItems,
            float velocity,
            float inaccuracy,
            boolean isCrit,
            @Nullable LivingEntity target
    ) {
        super.shoot(level, shooter, hand, weapon, projectileItems, velocity * VELOCITY_MULTIPLIER, inaccuracy * INACCURACY_MULTIPLIER, isCrit, target);
    }
}
