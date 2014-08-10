package MiscUtils.GuiObjects.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ModSlotArmor extends Slot{
	
	final int ArmorType;
	final EntityPlayer Player;

	public ModSlotArmor(IInventory par1iInventory, int par2, int par3, int par4, int ArmorType, EntityPlayer Player) {
		super(par1iInventory, par2, par3, par4);
		this.ArmorType = ArmorType;
		this.Player = Player;
	}
	
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
        return item != null && item.isValidArmor(par1ItemStack, ArmorType, Player);
    }
    
    public IIcon getBackgroundIconIndex()
    {
        return ItemArmor.func_94602_b(this.ArmorType);
    }

}
