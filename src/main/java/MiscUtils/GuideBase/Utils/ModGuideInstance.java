package MiscUtils.GuideBase.Utils;

import java.util.ArrayList;

public class ModGuideInstance {

    public ArrayList list;
    public String Id;
    public GuideInstance guide;

    public ModGuideInstance(ArrayList tabList, String id, GuideInstance instance){
        this.list = tabList;
        this.Id = id;
        this.guide = instance;

    }
}
