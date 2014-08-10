package MiscUtils.Block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public abstract class ModBlockContainer extends ModBlock implements ITileEntityProvider {
    protected ModBlockContainer(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.isBlockContainer = true;

    }

    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }


    @Override
    public void breakBlock(World World, int x, int y, int z, Block id, int meta)
    {
        TileEntity tile = World.getTileEntity(x, y, z);

        if(tile != null && tile instanceof IInventory){
            IInventory inv = (IInventory)tile;

            for(int i = 0; i < inv.getSizeInventory(); i++){
                ItemStack stack = inv.getStackInSlotOnClosing(i);

                if(stack != null){
                    float spawnX = x + World.rand.nextFloat();
                    float spawnY = y + World.rand.nextFloat();
                    float spawnZ = z + World.rand.nextFloat();


                    EntityItem droppedItem = new EntityItem(World, spawnX, spawnY, spawnZ, stack);

                    float mult = 0.05F;

                    droppedItem.motionX = (-0.5 + World.rand.nextFloat()) * mult;
                    droppedItem.motionY = (4 + World.rand.nextFloat()) * mult;
                    droppedItem.motionZ = (-0.5 + World.rand.nextFloat()) * mult;


                    World.spawnEntityInWorld(droppedItem);



                }

            }

        }

        super.breakBlock(World, x, y, z, id, meta);
        World.removeTileEntity(x, y, z);

    }
}
