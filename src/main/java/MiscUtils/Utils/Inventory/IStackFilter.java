package MiscUtils.Utils.Inventory;

import net.minecraft.item.ItemStack;

public interface IStackFilter {

    public boolean matches(ItemStack stack);
}