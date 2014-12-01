package MiscUtils.Block;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
                    List<ItemStack> items = block.getDrops(world, pos, world.getBlockState(pos), block.getMetaFromState(world.getBlockState(pos)));

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
    public static List<ItemStack> getItemStackFromBlock(World world, int x, int y, int z, int fortune) {
         Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
         if (block == null){
             return null;
             }
        
                 if (world.isAirBlock(new BlockPos(x, y, z))){
             return null;
             }

              return block.getDrops(world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)), fortune);
         }
    
             public static void breakBlock(World world, int x, int y, int z) {
         breakBlock(world, x, y, z, 1200);
         }
    
             public static void breakBlockToPlayer(World world, int x, int y, int z, EntityPlayer player){
         if (!world.isAirBlock(new BlockPos(x, y, z)) && !world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
             Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
             List<ItemStack> items = block.getDrops(world,new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)), block.getMetaFromState(world.getBlockState(new BlockPos(x, y, z))));
            
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
                            entityitem.setPickupDelay(10);
                    
                             world.spawnEntityInWorld(entityitem);
                    
                             }
                 }
             }
        
                 world.setBlockState(new BlockPos(x, y, z), Blocks.air.getDefaultState());
         }

}