package MiscUtils.GuideBase.Gui.Utils;

import MiscUtils.GuideBase.Utils.GuideInstance;
import MiscUtils.MiscUtilsMain;
import MiscUtils.Render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuideModSelectionButton extends GuiButton {
    public GuideInstance instance = null;
    boolean Selected;

    public GuideModSelectionButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, GuideInstance gd, boolean Selected) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, 30, 28, "");
        instance = gd;
        this.Selected = Selected;

    }
    public GuideInstance GetInstance(){
        return instance;
    }


    public ResourceLocation IconTexutre = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGuiIcons.png");
    protected static RenderItem itemRender = new RenderItem();

    public void drawButton(Minecraft mc, int x, int y)
    {

        GL11.glPushMatrix();
            mc.getTextureManager().bindTexture(IconTexutre);
            this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;


            this.mouseDragged(mc, x, y);
            int mode = 0;

             if (this.field_146123_n)
            {
                mode = 1;
            }
        int xx = xPosition;
        int yy = yPosition;


        if(Selected) {
            xx -= 3;
            mode = 1;

            drawTexturedModalRect(xx + 30, yy, 4,29 ,3, 28);
        }

        if(mode == 0){
            drawTexturedModalRect(xx, yy, 0,0 , 30, 28);
        }else{
            drawTexturedModalRect(xx, yy, 0,29 ,30, 28);
        }

        if(instance.ModPageDisplay() != null)
            RenderHelper.drawItemStack(mc.fontRenderer, instance.ModPageDisplay(), xx + 7, yy + 5);


        GL11.glPopMatrix();
    }

}
