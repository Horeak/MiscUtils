package MiscUtils.Block;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public abstract class ModBlockCustomModel extends ModBlockContainer {
    protected ModBlockCustomModel(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l){
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}
