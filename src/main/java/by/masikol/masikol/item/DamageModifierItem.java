package by.masikol.masikol.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// The four tiers only differ in proc chance and name color - the flat damage bonus is the same
// for all of them (see ModAccessoryItems). Color is baked into the display name directly instead
// of vanilla Rarity, since the requested palette (blue/orange/purple/gold) doesn't match vanilla's
// own rarity colors.
public class DamageModifierItem extends Item implements IAccessoryItem {

    private final float procChance;
    private final float damageBonus;
    private final int nameColor;

    public DamageModifierItem(Properties properties, float procChance, float damageBonus, int nameColor) {
        super(properties);
        this.procChance = procChance;
        this.damageBonus = damageBonus;
        this.nameColor = nameColor;
    }

    public float getProcChance() {
        return procChance;
    }

    public float getDamageBonus() {
        return damageBonus;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(Style.EMPTY.withColor(TextColor.fromRgb(nameColor)));
    }
}
