package MiscUtils.Block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public abstract class ModBlockContainer extends ModBlock implements ITileEntityProvider {
    public ModBlockContainer(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.isBlockContainer = true;


    }


    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
            TileEntity tileentity = worldIn.getTileEntity(pos);

        if(tileentity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }



        super.breakBlock(worldIn, pos, state);
    }


}
