package MiscUtils.Utils.Block;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import java.util.List;

public class BlockUtil {

    public static List<ItemStack> getItemStackFromBlock(World world, int x, int y, int z, int fortune) {
            Block block = world.getBlock(x, y, z);

            if (block == null){
                    return null;
            }

            if (world.isAirBlock(x, y, z)){
                    return null;
            }

            
            int meta = world.getBlockMetadata(x, y, z);
            

            return block.getDrops(world, x, y, z, meta, fortune);
    }

    public static void breakBlock(World world, int x, int y, int z) {
            breakBlock(world, x, y, z, 1200);
    }

    public static void breakBlock(World world, int x, int y, int z, int forcedLifespan) {
            if (!world.isAirBlock(x, y, z) && !world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
                    Block block = world.getBlock(x, y, z);
                    List<ItemStack> items = block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);

                    for (ItemStack item : items) {
                            float var = 0.7F;
                            double dx = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                            double dy = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                            double dz = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                            EntityItem entityitem = new EntityItem(world, x + dx, y + dy, z + dz, item);

                            entityitem.lifespan = forcedLifespan;
                            entityitem.delayBeforeCanPickup = 10;

                            world.spawnEntityInWorld(entityitem);
                    }
            }

            world.setBlock(x, y, z, Blocks.air);
    }

    public static boolean canChangeBlock(World world, int x, int y, int z) {
            return canChangeBlock(world.getBlock(x, y, z), world, x, y, z);
    }

    public static boolean isAnObstructingBlock(Block block, World world, int x, int y, int z) {

            if (block == null || block.isAir(world, x, y, z))
                    return false;
            return true;
    }

    public static boolean canChangeBlock(Block block, World world, int x, int y, int z) {

            if (block == Blocks.air || block == null || block.isAir(world, x, y, z))
                    return true;

            if (block.getBlockHardness(world, x, y, z) < 0)
                    return false;


            if (block == Blocks.lava || block == Blocks.flowing_lava)
                    return false;

            return true;
    }





    public static boolean isToughBlock(World world, int x, int y, int z) {
    	Block block = world.getBlock(x, y, z);
    	
            return !block.getMaterial().isToolNotRequired();
    }

    public static boolean isFullFluidBlock(World world, int x, int y, int z) {
            return isFullFluidBlock(world.getBlock(x, y, z), world, x, y, z);
    }
    

    public static boolean isFullFluidBlock(Block block, World world, int x, int y, int z) {
            if (block instanceof BlockFluidBase || block instanceof IFluidBlock)
                    return world.getBlockMetadata(x, y, z) == 0;
            return false;
    }

    public static Fluid getFluid(Block block) {
            if (block instanceof IFluidBlock) {
                    return ((IFluidBlock) block).getFluid();
            } else if (block == Blocks.water || block == Blocks.flowing_water) {
                    return FluidRegistry.WATER;
            } else if (block == Blocks.lava || block == Blocks.flowing_lava) {
                    return FluidRegistry.LAVA;
            }
            return null;
    }

    public static FluidStack drainBlock(World world, int x, int y, int z, boolean doDrain) {
            return drainBlock(world.getBlock(x, y, z), world, x, y, z, doDrain);
    }

    public static FluidStack drainBlock(Block block, World world, int x, int y, int z, boolean doDrain) {
            if (block instanceof IFluidBlock) {
                    IFluidBlock fluidBlock = (IFluidBlock) block;
                    if (fluidBlock.canDrain(world, x, y, z))
                            return fluidBlock.drain(world, x, y, z, doDrain);
            } else if (block == Blocks.water || block == Blocks.flowing_water) {
                    int meta = world.getBlockMetadata(x, y, z);
                    if (meta != 0)
                            return null;
                    if (doDrain)
                            world.setBlockToAir(x, y, z);
                    return new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
            } else if (block == Blocks.lava || block == Blocks.flowing_lava) {
                    int meta = world.getBlockMetadata(x, y, z);
                    if (meta != 0)
                            return null;
                    if (doDrain)
                            world.setBlockToAir(x, y, z);
                    return new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);
            }
            return null;
    }


    @SuppressWarnings("unchecked")
    public static void explodeBlock(World world, int x, int y, int z) {
            if (FMLCommonHandler.instance().getEffectiveSide().isClient())
                    return;

            Explosion explosion = new Explosion(world, null, x + .5, y + .5, z + .5, 3f);
            explosion.affectedBlockPositions.add(new ChunkPosition(x, y, z));
            explosion.doExplosionB(true);

            for (EntityPlayer player : (List<EntityPlayer>) world.playerEntities) {
                    if (!(player instanceof EntityPlayerMP))
                            continue;

                    if (player.getDistanceSq(x, y, z) < 4096) {
                            ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S27PacketExplosion(x + .5, y + .5, z + .5, 3f, explosion.affectedBlockPositions, null));
                    }
            }
    }
}