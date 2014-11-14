package MiscUtils.GuideBase.Utils;

public class ModGuideText extends TextGuideTab {
    public ModGuideText(GuideInstance gd, Object stack, String Name) {
        super(gd, stack, Name);
    }

    @Override
    public String GetText() {
        return this.instance.ModDescription();
    }
}
