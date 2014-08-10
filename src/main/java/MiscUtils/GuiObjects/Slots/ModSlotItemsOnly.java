package MiscUtils.GuiObjects.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModSlotItemsOnly extends Slot{

	Item[] item;
	
	public ModSlotItemsOnly(IInventory arg0, int arg1, int arg2, int arg3, Item[] items) {
		super(arg0, arg1, arg2, arg3);
		item = items;
	}

	
    public boolean isItemValid(ItemStack stack)
    {
    	
    	for(int i = 0; i < item.length; i++){
    		if(stack.getItem().equals(item[i]))
    		return true;
    		
    	}
        return false;
    }
}
