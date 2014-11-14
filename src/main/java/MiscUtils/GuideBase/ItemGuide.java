package MiscUtils.GuideBase;

import MiscUtils.MiscUtilsMain;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemGuide extends Item {

    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        FMLNetworkHandler.openGui(p_77659_3_, MiscUtilsMain.instance, 1, p_77659_2_, 0, 0, 0);
        return p_77659_1_;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {

        list.add(StatCollector.translateToLocal("item.desc.guide.miscitemsmods"));

    }

}
