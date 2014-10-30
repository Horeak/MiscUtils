package MiscUtils.Render;

import MiscUtils.TileEntity.IBlockInfo;
import MiscUtils.Utils.RayTracing;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class TileEntityBlockInfoRender extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity te, double xx, double yy, double zz, float p_147500_8_) {


        if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
            if(RayTracing.instance().getTarget() != null)
                if(RayTracing.instance().getTarget().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

                    int x = RayTracing.instance().getTarget().blockX;
                    int y = RayTracing.instance().getTarget().blockY;
                    int z = RayTracing.instance().getTarget().blockZ;

                    World world = Minecraft.getMinecraft().thePlayer.getEntityWorld();

                    if(te.xCoord == x && te.yCoord == y && te.zCoord == z)
                    if(world.getTileEntity(x,y,z) instanceof IBlockInfo) {
                        TileEntity tile = world.getTileEntity(x,y,z);
                        IBlockInfo info = (IBlockInfo)tile;


                        ArrayList<String> Strings = new ArrayList<String>();
                        info.Info(Strings);

                        RenderHelper.RenderInfoTagOverTileEntity(tile, Strings, xx, yy, zz);
                    }
                }
    }

}
