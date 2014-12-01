package MiscUtils.Inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Used from BuildCraft source
 */
public class TransactorSpecial extends Transactor {

    protected ISpecialInventory inventory;

    public TransactorSpecial(ISpecialInventory inventory) {
            this.inventory = inventory;
    }


    @Override
    public int inject(ItemStack stack, EnumFacing orientation, boolean doAdd) {
            return inventory.addItem(stack, doAdd, orientation);
    }

    @Override
    public ItemStack remove(IStackFilter filter, EnumFacing orientation, boolean doRemove) {
            ItemStack[] extracted = inventory.extractItem(false, orientation, 1);
            if (extracted != null && extracted.length > 0 && filter.matches(extracted[0])) {
                    if (doRemove) {
                            inventory.extractItem(true, orientation, 1);
                    }
                    return extracted[0];
            }
            return null;
    }
}