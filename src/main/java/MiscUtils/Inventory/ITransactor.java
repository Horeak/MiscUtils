package MiscUtils.Inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Used from BuildCraft source
 */
public interface ITransactor {

    /**
     * Adds an Item to the inventory.
     *
     * @param stack
     * @param orientation
     * @param doAdd
     * @return The ItemStack, with stackSize equal to amount moved.
     */
    ItemStack add(ItemStack stack, EnumFacing orientation, boolean doAdd);

    /**
     * Removes and returns a single item from the inventory matching the filter.
     *
     * @param filter
     * @param orientation
     * @param doRemove
     * @return
     */
    ItemStack remove(IStackFilter filter, EnumFacing orientation, boolean doRemove);
}