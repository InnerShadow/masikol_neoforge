package by.masikol.masikol.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Bows aren't tiered by material like tools/armor - there's no Tier to plug in for a damage/speed
// boost. BowItem.getPowerForTime is static, so overriding it wouldn't change anything (releaseUsing
// calls it unqualified, bound to BowItem at compile time). shoot() is the lowest instance method
// both BowItem and CrossbowItem share, so it's the actual hook for boosting velocity/accuracy.
public class MithrilBowItem extends BowItem {

    private static final float VELOCITY_MULTIPLIER = 1.15F;
    private static final float INACCURACY_MULTIPLIER = 0.85F;

    public MithrilBowItem(Item.Properties properties) {
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
