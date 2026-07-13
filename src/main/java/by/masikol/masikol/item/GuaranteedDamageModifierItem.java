package by.masikol.masikol.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Same idea as DamageModifierItem, but the bonus always applies (no proc chance) - a separate item
// type on purpose, so the "only one at a time" exclusivity rules for chance-based and guaranteed
// modifiers are independent: one of each can be worn together, just not two of the same category.
public class GuaranteedDamageModifierItem extends Item implements IAccessoryItem {

    private final float damageBonus;
    private final int nameColor;

    public GuaranteedDamageModifierItem(Properties properties, float damageBonus, int nameColor) {
        super(properties);
        this.damageBonus = damageBonus;
        this.nameColor = nameColor;
    }

    public float getDamageBonus() {
        return damageBonus;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(Style.EMPTY.withColor(TextColor.fromRgb(nameColor)));
    }
}
