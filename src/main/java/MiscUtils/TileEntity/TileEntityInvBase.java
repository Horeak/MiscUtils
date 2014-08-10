package MiscUtils.TileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class TileEntityInvBase  extends ModTileEntity implements IInventory{



	public String Name;
	public ItemStack[] Inv;
	public int SlotSize;
	
	public NBTTagCompound nbt;
	
	
	@Override
	public String getInventoryName() {
		return Name;
	}



	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}



	@Override
	public void openInventory() {
		
	}



	@Override
	public void closeInventory() {
		
	}
	
	public TileEntityInvBase(int Slots, String Name, int Size){
		

		Inv = new ItemStack[Slots];
		this.Name = Name;
		this.SlotSize = Size;
		
	}
	
	@Override
	public int getSizeInventory() {
		return Inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return Inv[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemstack = getStackInSlot(i);
		
		if(itemstack != null){
			
			if(itemstack.stackSize <= j){
				
				setInventorySlotContents(i, null);
			}else{
				
				itemstack = itemstack.splitStack(j);
				
			}
			
		}
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		
		setInventorySlotContents(i, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
	
		Inv[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()){
			itemstack.stackSize = getInventoryStackLimit();
			
		}
		
		
	}


	@Override
	public int getInventoryStackLimit() {
		return SlotSize;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
		
	}
	
	

        
        @Override
    	public void writeToNBT(NBTTagCompound compound){
    		super.writeToNBT(compound);
    		
    		NBTTagList Items = new NBTTagList();
    		
    		for (int i = 0; i < getSizeInventory(); i++){
    			
    			ItemStack stack = getStackInSlot(i);
    			if(stack != null){
    				
    				NBTTagCompound item = new NBTTagCompound();
    				item.setByte("Slot", (byte)i);
    				stack.writeToNBT(item);
    				Items.appendTag(item);
    			}
    		}

    		compound.setTag("Items", Items);
    		
    		
    		
    		
    	}
    	
    	@Override
    	public void readFromNBT(NBTTagCompound compound){
    		super.readFromNBT(compound);

    		NBTTagList nbttaglist = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
    		Inv = new ItemStack[getSizeInventory()];
            for (int i = 0; i < nbttaglist.tagCount(); i++)
            {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j >= 0 && j < Inv.length)
                {
                    this.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
    		
    		
    		
    	}
}