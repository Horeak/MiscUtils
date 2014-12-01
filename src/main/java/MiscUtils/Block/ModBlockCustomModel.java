package MiscUtils.Block;

import net.minecraft.block.material.Material;

public abstract class ModBlockCustomModel extends ModBlockContainer {
    protected ModBlockCustomModel(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    @Override
    public int getRenderType() {
        return -1;
    }


    @Override
    public boolean isOpaqueCube(){
        return false;
    }

}
