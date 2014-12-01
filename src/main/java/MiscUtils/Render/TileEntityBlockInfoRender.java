package MiscUtils.Render;

import MiscUtils.TileEntity.IBlockInfo;
import MiscUtils.Utils.RayTracing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

public abstract class TileEntityBlockInfoRender extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity te, double xx, double yy, double zz, float p_147500_8_, int t) {


        if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
            if(RayTracing.instance().getTarget() != null)
                if(RayTracing.instance().getTarget().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

                    int x = RayTracing.instance().getTarget().func_178782_a().getX();
                    int y = RayTracing.instance().getTarget().func_178782_a().getY();
                    int z = RayTracing.instance().getTarget().func_178782_a().getZ();

                    World world = Minecraft.getMinecraft().thePlayer.getEntityWorld();

                    if(te.getPos().getX() == x && te.getPos().getY() == y && te.getPos().getZ() == z)
                    if(world.getTileEntity(new BlockPos(x,y,z)) instanceof IBlockInfo) {
                        TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
                        IBlockInfo info = (IBlockInfo)tile;


                        ArrayList<String> Strings = new ArrayList<String>();
                        info.Info(Strings);

                        RenderHelper.RenderInfoTagOverTileEntity(tile, Strings, xx, yy, zz);
                    }
                }
    }

}
