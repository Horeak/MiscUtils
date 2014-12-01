package MiscUtils.Item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ModItemWithDamage extends ItemTool{

    public static Item.ToolMaterial NullMaterial = EnumHelper.addToolMaterial("NullMaterial", 0, 0, 0, 0, 0);

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return false;
    }


    int MaxDamage;

    protected ModItemWithDamage(int ItemMaxDamage) {
        super(0, NullMaterial, Sets.newHashSet());
        this.setMaxDamage(ItemMaxDamage);

        MaxDamage = ItemMaxDamage;
    }


    public boolean hitEntity(ItemStack stack, EntityLivingBase EntityHit, EntityLivingBase EntityAttacker)
    {
        return false;
    }
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_){

        return false;
    }

    public int getItemEnchantability()
    {
        return 0;
    }

    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        return 0;
    }

    @Override
    public float getDigSpeed(ItemStack stack, net.minecraft.block.state.IBlockState state)
    {
        return 0.01F;
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    { }


    public float getDamageVsEntity()
    {
        return 0;
    }

}
