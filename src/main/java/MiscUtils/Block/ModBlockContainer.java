package MiscUtils.Block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;


public abstract class ModBlockContainer extends ModBlock implements ITileEntityProvider {
    public ModBlockContainer(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.isBlockContainer = true;

    }



}
