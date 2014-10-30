package MiscUtils.Block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public abstract class ModBlockCustomModel extends ModBlockContainer {
    protected ModBlockCustomModel(Material p_i45394_1_) {
        super(p_i45394_1_);
        setBlockTextureName("stone");
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

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return Blocks.stone.getIcon(p_149691_1_, p_149691_2_);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}
