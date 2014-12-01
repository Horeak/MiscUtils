package MiscUtils.Inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Used from BuildCraft source
 */

public abstract class Transactor implements ITransactor {

    @Override
    public ItemStack add(ItemStack stack, EnumFacing orientation, boolean doAdd) {
            ItemStack added = stack.copy();
            added.stackSize = inject(stack, orientation, doAdd);
            return added;
    }

    public abstract int inject(ItemStack stack, EnumFacing orientation, boolean doAdd);

    public static ITransactor getTransactorFor(Object object) {

            if (object instanceof ISpecialInventory)
                    return new TransactorSpecial((ISpecialInventory) object);
            else if (object instanceof ISidedInventory)
                    return new TransactorSimple((ISidedInventory) object);
            else if (object instanceof IInventory)
                    return new TransactorSimple(InventoryUtils.getInventory((IInventory) object));

            return null;
    }
}