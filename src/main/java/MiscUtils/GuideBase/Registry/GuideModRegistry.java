package MiscUtils.GuideBase.Registry;

import MiscUtils.GuideBase.Utils.GuideInstance;
import MiscUtils.GuideBase.Utils.ModGuideInstance;

import java.util.ArrayList;

public class GuideModRegistry {

    public static ArrayList<ModGuideInstance> ModArray = new ArrayList<ModGuideInstance>();

    public static void RegisterModToGuide(GuideInstance guide)
    {
        guide.RegisterInfo();
        ModArray.add(guide.GetModInstance());
    }
}
