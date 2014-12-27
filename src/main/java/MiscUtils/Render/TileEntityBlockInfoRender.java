package MiscUtils.Render;

import MiscUtils.TileEntity.IBlockInfo;
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
    public void renderTileEntityAt(TileEntity te, double xx, double yy, double zz, float scale, int f) {


        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MovingObjectPosition obj = Minecraft.getMinecraft().objectMouseOver;

            if (obj != null && obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&obj.func_178782_a() != null)
            {
                    int x = obj.func_178782_a().getX();
                    int y = obj.func_178782_a().getY();
                    int z = obj.func_178782_a().getZ();

                    World world = Minecraft.getMinecraft().thePlayer.getEntityWorld();

                    if (te.getPos().getX() == x && te.getPos().getY() == y && te.getPos().getZ() == z)
                        if (world.getTileEntity(new BlockPos(x, y, z)) instanceof IBlockInfo) {
                            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                            IBlockInfo info = (IBlockInfo) tile;


                            ArrayList<String> Strings = new ArrayList<String>();
                            info.Info(Strings);

                            RenderHelper.RenderInfoTagOverTileEntity(tile, Strings, xx, yy, zz);
                        }
                }
            }

    }

}
