package MiscUtils.Block;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;


public class BlockUtil {



    public static void breakBlock(World world, int x, int y, int z, int forcedLifespan) {
        BlockPos pos = new BlockPos(x,y,z);

            if (!world.isAirBlock(pos) && !world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
                    Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
                    List<ItemStack> items = block.getDrops(world, pos, world.getBlockState(pos), 0);

                    for (ItemStack item : items) {
                            float var = 0.7F;
                            double dx = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                            double dy = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                            double dz = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                            EntityItem entityitem = new EntityItem(world, x + dx, y + dy, z + dz, item);

                            entityitem.lifespan = forcedLifespan;
                            entityitem.setPickupDelay(10);

                            world.spawnEntityInWorld(entityitem);
                    }
            }

            world.setBlockState(pos, Blocks.air.getDefaultState());
    }


}