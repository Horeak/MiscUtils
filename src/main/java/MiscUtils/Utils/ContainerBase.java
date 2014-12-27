package MiscUtils.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container{
    
    public abstract IInventory getTile();
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();


            if (slot < getTile().getSizeInventory())
            {
                if (!mergeItemStack(stackInSlot, getTile().getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(stackInSlot, 0, getTile().getSizeInventory(), false))
            {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }


    protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
    {
        boolean flag1 = false;
        int k = p_75135_2_;

        if (p_75135_4_)
        {
            k = p_75135_3_ - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (p_75135_1_.isStackable())
        {
            while (p_75135_1_.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == p_75135_1_.getItem() && (!p_75135_1_.getHasSubtypes() || p_75135_1_.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(p_75135_1_, itemstack1))
                {
                    int l = itemstack1.stackSize + p_75135_1_.stackSize;

                    if (l <= p_75135_1_.getMaxStackSize())
                    {
                        p_75135_1_.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < p_75135_1_.getMaxStackSize())
                    {
                        p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = p_75135_1_.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (p_75135_4_)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (p_75135_1_.stackSize > 0)
        {
            if (p_75135_4_)
            {
                k = p_75135_3_ - 1;
            }
            else
            {
                k = p_75135_2_;
            }

            while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null  && slot.isItemValid(p_75135_1_))
                {
                    slot.putStack(p_75135_1_.copy());
                    slot.onSlotChanged();
                    p_75135_1_.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (p_75135_4_)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }
}
