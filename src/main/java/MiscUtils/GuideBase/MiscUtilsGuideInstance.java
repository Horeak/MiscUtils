package MiscUtils.GuideBase;

import MiscUtils.GuideBase.Utils.GuideInstance;
import MiscUtils.GuideBase.Utils.ModGuideText;
import MiscUtils.MiscUtilsMain;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MiscUtilsGuideInstance extends GuideInstance {


    @Override
    public ResourceLocation BlockDescriptions() {
        return new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "guide/blockinfo.properties");
    }

    @Override
    public ResourceLocation ItemDescriptions() {
        return new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "guide/iteminfo.properties");
    }

    @Override
    public String ModPageName() {
        return "guide.mod.name";
    }

    @Override
    public ItemStack ModPageDisplay() {
        return new ItemStack(MiscUtilsMain.Guide);
    }

    @Override
    public String ModDescription() {
        return "This guide book will show information about all blocks and items which has been added using MiscUtils. The guide will show recipes for all items as well as recipes in custom machines if the modder has added support for it.";
    }

    ModGuideText MainTab;

    @Override
    public void RegisterInfo() {
        MainTab = new ModGuideText(this, Items.paper, "guide.mod.tab.main");




        RegisterTab(MainTab);
    }
}
