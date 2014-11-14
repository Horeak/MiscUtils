package MiscUtils.GuideBase.Utils;

public abstract class TextGuideTab extends GuideTab {
    public TextGuideTab(GuideInstance gd, Object stack, String Name) {
        super(gd, stack, Name);
    }

    public abstract String GetText();
}
