package MiscUtils.Inventory;

import net.minecraft.item.ItemStack;


/**
 * Used from BuildCraft source
 */
public interface IStackFilter {

    public boolean matches(ItemStack stack);
}