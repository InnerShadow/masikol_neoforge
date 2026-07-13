package by.masikol.masikol.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Third independent damage-modifier category (see DamageModifierItem, GuaranteedDamageModifierItem):
// always applies, but the bonus is picked uniformly from [minBonus, maxBonus] each hit instead of
// being a fixed amount or a chance to apply a fixed amount.
public class RandomDamageModifierItem extends Item implements IAccessoryItem {

    private final float minBonus;
    private final float maxBonus;
    private final int nameColor;

    public RandomDamageModifierItem(Properties properties, float minBonus, float maxBonus, int nameColor) {
        super(properties);
        this.minBonus = minBonus;
        this.maxBonus = maxBonus;
        this.nameColor = nameColor;
    }

    public float getMinBonus() {
        return minBonus;
    }

    public float getMaxBonus() {
        return maxBonus;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(Style.EMPTY.withColor(TextColor.fromRgb(nameColor)));
    }
}
