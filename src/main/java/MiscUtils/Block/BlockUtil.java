package MiscUtils.Block;

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

    public static void breakBlockToPlayer(World world, int x, int y, int z, EntityPlayer player){
        if (!world.isAirBlock(x, y, z) && !world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            Block block = world.getBlock(x, y, z);
            List<ItemStack> items = block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);

            for (ItemStack item : items) {

                if(!player.capabilities.isCreativeMode)
                if(player.inventory.addItemStackToInventory(item)){

                }else{
                    float var = 0.7F;
                    double dx = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                    double dy = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                    double dz = world.rand.nextFloat() * var + (1.0F - var) * 0.5D;
                    EntityItem entityitem = new EntityItem(world, player.posX + dx, player.posY + dy, player.posZ + dz, item);

                    entityitem.lifespan = 1200;
                    entityitem.delayBeforeCanPickup = 10;

                    world.spawnEntityInWorld(entityitem);

                }
            }
        }

        world.setBlock(x, y, z, Blocks.air);
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