package MiscUtils.GuideBase.Utils;

import MiscUtils.Utils.StackUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public abstract class GuideInstance {

    public abstract ResourceLocation BlockDescriptions();
    public abstract ResourceLocation ItemDescriptions();

    public abstract String ModPageName();
    public abstract ItemStack ModPageDisplay();

    //Only used for ModGuideText Tabs
    public abstract String ModDescription();

    public ArrayList<GuideTab> GuideTabs = new ArrayList<GuideTab>();

    public abstract void RegisterInfo();

    public ModGuideInstance GetModInstance(){
        return new ModGuideInstance(GuideTabs, ModPageName(), this);
    }

    public void RegisterTab(GuideTab tab){
        GuideTabs.add(tab);

        for (Object r : tab.list) {
            ItemStack st = StackUtils.GetObject(r);
            tab.InitiateInfo(st);
        }


    }

}
